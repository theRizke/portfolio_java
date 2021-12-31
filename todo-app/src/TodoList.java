import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodoList {
  private static final String TODOS_ASSETS_FOLDER = "./todoApp/";
  private static final String TODOS_LOGFILE_FILEPATH = "./todoApp/todos.txt";
  List<Todo> todos;
  String[] args;


  public TodoList(String[] args) {
    this.args = args;
  }

  public void execute(){
    todos = fetchTodos(TODOS_LOGFILE_FILEPATH);
    startProgram(args);
    saveTodos(TODOS_LOGFILE_FILEPATH);
  }


  private void startProgram(String[] args) {
    if (args.length > 0) {
      readCommand(args);
    } else {
      System.out.println(getCommands());
    }
  }

  private void readCommand(String[] args) {
    String command = args[0];

    switch (command) {
      case "-l":
        System.out.println(listTodos(false));
        break;
      case "-la":
        System.out.println(listTodos(true));
        break;
      case "-a":
        addTodo(args);
        break;
      case "-r":
        removeTodo(args);
        break;
      case "-c":
        checkTodo(args);
        break;
      default:
        System.out.println("Unsupported argument");

    }
  }

  private void addTodo(String[] args) {
    try {
      todos.add(new Todo(args[1], false));
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Unable to add: no task provided.");
    }
  }

  private void removeTodo(String[] args) {
    try {
      int index = Integer.parseInt(args[1]);
      try {
        todos.remove(index - 1);
      } catch (IndexOutOfBoundsException e) {
        System.out.println("Unable to remove: index is out of bound");
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Unable to remove: no index provided");
    } catch (NumberFormatException e) {
      System.out.println("Unable to remove: index is not a number");
    }
  }

  private void checkTodo(String[] args) {
    try {
      int index = Integer.parseInt(args[1]);
      try {
        todos.get(index - 1).setDone(true);
      } catch (IndexOutOfBoundsException e) {
        System.out.println("Unable to check: index is out of bound");
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Unable to check: no index provided");
    } catch (NumberFormatException e) {
      System.out.println("Unable to check: index is not a number");
    }
  }

  private List<Todo> fetchTodos(String pathString) {
    List<Todo> todos = new ArrayList<>();
    Path filePath = Paths.get(pathString);

    if (!Files.exists(filePath)) {
      try {
        Files.createDirectories(Paths.get(TODOS_ASSETS_FOLDER));
        Files.createFile(filePath);
      } catch (IOException e) {
        System.out.println("Creating new log file is failed.");
      }
    }

    else{
      try {
        List<String> lines = Files.readAllLines(filePath);
        if(lines.size() > 0){
          todos = convertStringToTodos(lines);
        }
      } catch (NoSuchFileException e) {
        System.out.println("Tasks logfile doesn't exist.");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return todos;
  }

  private List<Todo> convertStringToTodos(List<String> lines) {
    List<Todo> todos = new ArrayList<>();
    lines.remove(lines.size() - 1); //Removing last empty "" row
    for (String line : lines) {
      String[] arrTodoInfo = line.split(";");
      String todo = arrTodoInfo[0];
      boolean isDone = !arrTodoInfo[1].equals("0");
      todos.add(new Todo(todo, isDone));
    }
    return todos;
  }

  private void saveTodos(String filePath) {
    Path filesPath = Paths.get(filePath);
    try {
      String output = getOutputString();
      Files.write(filesPath, Collections.singleton(output));
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  private String getCommands() {
    String commands = "Command Line Todo application\n";
    commands += "=============================\n\n";
    commands += "Command line arguments:\n";
    commands += "    -l   List all unchecked tasks\n";
    commands += "    -la  List all tasks\n";
    commands += "    -r   Adds a new task\n";
    commands += "    -r   Removes a task\n";
    commands += "    -c   Completes a tasks\n";

    return commands;
  }

  private String getOutputString() {
    String output = "";
    for (Todo todo : todos) {
      output += todo.getTodo() + ";" + (todo.isDone() ? "1" : "0") + "\n";
    }
    return output;
  }

  private String listTodos(boolean showAll) {

    StringBuilder todosString = new StringBuilder();

    if(todos.size() == 0 && !hasUnCheckedTodo()){
      return "Nincs mára tennivalód! :)";
    }

      int todoCounter = 1;
      for (int i = 0; i < todos.size(); i++) {

        if(showAll || !todos.get(i).isDone()){
          todosString.append(todoCounter + " - ");
        }

        if(showAll){
          todosString.append("[" + (todos.get(i).isDone() ? "x" : " ") + "] ");
        }

        if(showAll || !todos.get(i).isDone()){
          todosString.append(todos.get(i).getTodo() + "\n");
        }
        todoCounter++;
      }

    return todosString.toString();
  }

  private boolean hasUnCheckedTodo() {
    for(Todo todo : todos){
      if(!todo.isDone()){
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder todosString = new StringBuilder();
    if (todos.size() != 0) {
      int todoCounter = 1;
      for (int i = 0; i < todos.size(); i++) {
        todosString.append(todoCounter + " -");
        todosString.append(" [" + (todos.get(i).isDone() ? "x" : " ") + "] ");
        todosString.append(todos.get(i).getTodo() + "\n");
        todoCounter++;

      }
    } else {
      todosString.append("Nincs mára tennivalód! :)");
    }
    return todosString.toString();
  }
}
