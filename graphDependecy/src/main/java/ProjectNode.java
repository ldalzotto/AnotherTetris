public class ProjectNode {
    private ProjectNode parent;
    private int index;
    private String projectName;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "Node{" +
                "parent=" + parent +
                ", index=" + index +
                ", projectName='" + projectName + '\'' +
                '}';
    }

    public ProjectNode getParent() {
        return parent;
    }

    public void setParent(ProjectNode parent) {
        this.parent = parent;
    }

}
