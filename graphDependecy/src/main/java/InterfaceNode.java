import com.github.javaparser.ast.CompilationUnit;

public class InterfaceNode extends AClassNode {

    public CompilationUnit implCompilationUnit;

    @Override
    public String getClassPlantumlDefinition() {
        StringBuilder definition = new StringBuilder();
        definition.append("interface " + this.getClassName() + "{ \n");

        attributes.forEach(attr -> {
            definition.append(attr + "\n");
        });

        methods.forEach(method -> {
            definition.append(method + "\n");
        });

        definition.append("}");

        return definition.toString();
    }
}
