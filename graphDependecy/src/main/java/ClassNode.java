public class ClassNode extends AClassNode {

    @Override
    public String getClassPlantumlDefinition() {
        return "class " + this.getClassName() + "\n";
    }

    @Override
    public String toString() {
        return "ClassNode{" +
                "className='" + className + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }

}
