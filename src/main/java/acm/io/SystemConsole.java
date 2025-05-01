package acm.io;

class SystemConsole extends IOConsole {
   SystemConsole() {
   }

   protected ConsoleModel createConsoleModel() {
      return new SystemConsoleModel();
   }
}
