public class TodoApp {
  public static void main(String[] args) {
    TodoList todoList = new TodoList(args);
    todoList.execute();
  }
}
