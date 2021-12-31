public class Todo {
  private String todo;
  private boolean isDone;

  public Todo(String task, boolean isDone){
    this.todo = task;
    this.isDone = isDone;
  }

  public String getTodo() {
    return todo;
  }

  public void setTodo(String todo) {
    this.todo = todo;
  }

  public boolean isDone() {
    return isDone;
  }

  public void setDone(boolean done) {
    isDone = done;
  }

  @Override
  public String toString(){
    return todo + ";" + (isDone ? "1" : "0") + "\n";
  }
}
