import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Application {

    private static final String DEPENDENCY_FILE_GRAPH_PARAM = "fileInput";
    private static final String OUTPUT_DIR_PARAM = "outputDir";

    public static void main(String[] argc){

        String fileArg = "";
        String outputDir = "";
        for (String arg :
                argc) {
            if (arg.contains(DEPENDENCY_FILE_GRAPH_PARAM)) {
                fileArg = arg.split("=")[1];
            }
            if(arg.contains(OUTPUT_DIR_PARAM)){
                outputDir = arg.split("=")[1];
            }
        }

        new File(Paths.get(outputDir).toUri()).mkdir();

        try {
            List<ProjectNode> rawNodes =
                Files.lines(Paths.get(fileArg).toAbsolutePath())
                        .filter(line -> line.contains("project :"))
                        .map(line -> {
                            ProjectNode node = new ProjectNode();
                            int index = line.indexOf("+");
                            if(index == -1){
                                index = line.indexOf("\\");
                            }
                            node.setIndex(index);
                            node.setProjectName(line.split("project :")[1].replaceAll(" \\(\\*\\)", ""));
                            return node;
                        })
                        .collect(Collectors.toList());

            List<ProjectNode> parentedNodes =
                    rawNodes.stream()
                    .map(node -> {
                        int currentListIndex = rawNodes.indexOf(node);
                        int currentNodeIndex = node.getIndex();

                        for (int i = currentListIndex; i >= 0; i--){
                            if(rawNodes.get(i).getIndex() < currentNodeIndex){
                                node.setParent(rawNodes.get(i));
                                break;
                            }
                        }

                        return node;
                    }).collect(Collectors.toList());

            List<String> plantuml =
                parentedNodes.stream()
                        .map(node -> {
                            if(node.getParent() != null){
                                return "[" + node.getParent().getProjectName() + "] --> [" + node.getProjectName() + "]";
                            }
                            return null;
                       })
                         .filter(plantumlLine -> plantumlLine != null)
                        .collect(Collectors.toList());

            plantuml.add(0, "scale 3840*2160");
            plantuml.add(0, "@startuml");
            plantuml.add("@enduml");

            Files.write(Paths.get(outputDir + "/output.txt").toAbsolutePath(), plantuml);

// CLASS DEPENDENCIES

            List<AClassNode> classNodes = new ArrayList<>();

            parentedNodes.stream()
                    .map(projectNode -> projectNode.getProjectName())
                    .distinct()
                    .map(projectName -> {
                        try {
                            return Files.walk(Paths.get("../" + projectName))
                                    .map(path -> {
                                        String filename = path.toFile().getName();

                                        AClassNode classNode = null;

                                        if(filename.contains(".java")){
                                            if(filename.charAt(0) == 'I'){
                                                classNode = new InterfaceNode();
                                                classNode.setClassName(filename.replaceAll(".java", ""));
                                                classNode.setProjectName(projectName);
                                            } else {
                                                classNode = new ClassNode();
                                                classNode.setClassName(filename.replaceAll(".java", ""));
                                                classNode.setProjectName(projectName);
                                            }
                                            classNode.filePath = path;

                                            try {
                                                classNode.compilationUnit = JavaParser.parse(path);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        return classNode;
                                    })
                                    .filter(aClassNode -> aClassNode!=null)
                                    .collect(Collectors.toList());

                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }).filter(value -> value!=null)
                    .forEach(lclass -> {
                        classNodes.addAll(lclass);
                    });

             //creating class
            Map<String, List<AClassNode>> mapProjectNameClassnode = new HashMap<>();

            classNodes.forEach(classNode -> {

                String projectName = classNode.getProjectName();
                if(mapProjectNameClassnode.get(projectName) == null){
                    mapProjectNameClassnode.put(projectName, Arrays.asList(classNode));
                } else {
                    List<AClassNode> tmp = mapProjectNameClassnode.get(projectName);

                    List<AClassNode> classNodeToAdd = new ArrayList<>();
                    classNodeToAdd.add(classNode);
                    classNodeToAdd.addAll(tmp);

                    mapProjectNameClassnode.put(projectName, classNodeToAdd);
                }

            });

            //generate implementation parser
            mapProjectNameClassnode.forEach((projectName, aClasses) -> {

                for (AClassNode aClassNode :
                        aClasses) {
                    if (aClassNode instanceof InterfaceNode) {

                        //get impl
                        AClassNode implementation = null;
                        String implementationName = aClassNode.className.substring(1);
                        for (AClassNode aClassNode1 :
                                aClasses) {
                            if(aClassNode1.className.equals(implementationName)){
                                implementation = aClassNode1;
                            }
                        }
                        if(implementation!=null){
                            try {
                                ((InterfaceNode) aClassNode).implCompilationUnit = JavaParser.parse(implementation.filePath);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }

            });


            Map<String, AClassNode> mapProjectNameToSingleClass = new HashMap<>();
            mapProjectNameClassnode.forEach((projectName, listClass) -> {
                AClassNode associatedClassNode = null;
                for (AClassNode aClassNode :
                        listClass) {
                    if (aClassNode instanceof InterfaceNode) {
                        associatedClassNode = aClassNode;
                    }
                }
                if(associatedClassNode == null){
                    associatedClassNode = listClass.get(0);
                }

                //set methods names
                associatedClassNode.methods = associatedClassNode.compilationUnit.getTypes().get(0).getMethods()
                        .stream().map(methodDeclaration -> methodDeclaration.getDeclarationAsString(true, false, true))
                        .collect(Collectors.toList());


                //set attributes
                if(associatedClassNode instanceof InterfaceNode){
                    if(((InterfaceNode) associatedClassNode).implCompilationUnit != null){
                        CompilationUnit implCompilationUnit = ((InterfaceNode) associatedClassNode).implCompilationUnit;

                        associatedClassNode.attributes = implCompilationUnit.getTypes().get(0).getFields().stream()
                                .filter(field -> {
                                    return (!field.getVariable(0).getName().getIdentifier().equals("instance"));
                                })
                                .map(field -> {
                                    return field.toString().split("=")[0];
                                })
                                .collect(Collectors.toList());
                    }
                }

                mapProjectNameToSingleClass.put(projectName, associatedClassNode);
            });


            List<String> outputClass =
            mapProjectNameToSingleClass.keySet().stream().map(projectName -> {
                AClassNode classNode = mapProjectNameToSingleClass.get(projectName);
                StringBuilder tmpClass = new StringBuilder();

                tmpClass.append(classNode.getClassPlantumlDefinition());

                return tmpClass.toString();
            }).collect(Collectors.toList());


            Map<String, String> mapProjectNameInterfaceName = new HashMap<>();
            mapProjectNameClassnode.keySet().stream()
                    .forEach(projectName -> {
                        mapProjectNameInterfaceName.put(projectName, mapProjectNameToSingleClass.get(projectName).className);
                    });

            outputClass.addAll(
                    parentedNodes.stream()
                            .map(node -> {
                                if(node.getParent() != null){
                                    return "\"" + mapProjectNameInterfaceName.get(node.getParent().getProjectName()) + "\" --> \"" +
                                            mapProjectNameInterfaceName.get(node.getProjectName()) + "\"";
                                }
                                return null;
                            })
                            .filter(plantumlLine -> plantumlLine != null)
                            .collect(Collectors.toList()));

            outputClass.add(0, "scale 3840*2160");
            outputClass.add(0, "@startuml");
            outputClass.add("@enduml");

            File outPutDit = new File(outputDir + "/output_class.txt");

            Files.write(outPutDit.toPath().toAbsolutePath(), outputClass);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
