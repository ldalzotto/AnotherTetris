import com.github.javaparser.ast.CompilationUnit;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class AClassNode implements IClassDefinition{

    protected String projectName;
    protected String className;
    public Path filePath;

    public CompilationUnit compilationUnit;

    public List<String> methods = new ArrayList<>();
    public List<String> attributes = new ArrayList<>();


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
