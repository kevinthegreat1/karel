package acm.util;

import java.security.Permission;

class SecurityManagerThatFails extends SecurityManager {
   SecurityManagerThatFails() {
   }

   public void checkRead(String var1) {
      throw new SecurityException("always fail");
   }

   public void checkPermission(Permission var1) {
   }
}
