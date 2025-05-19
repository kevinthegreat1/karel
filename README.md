# Karel and ACM for Java 24 and Above

## Usage

### Using IntelliJ Build System

#### Creating a new IntelliJ project using Java 24 or above
1. Open the `New Project` dialog in IntelliJ.
2. Select `Java` on the left.
3. Name your project and select the location.
4. For `JDK`, select 24 or higher.
   * If 24 or higher is not available in the `Rejistered JDKs` list, select `Download JDK`.
   * For `Version`, select 24 or higher.
   * Click `Select` and wait for the download to finish.
5. Click `Create` to create the project.

#### Adding Karel or ACM to your project
1. Open `File -> Project Structure...`.
2. Select `Libraries` on the left.
3. Click the `+` button on the top of the second column from the left.
4. Select `Java` from the dropdown.
5. Select the `karel` or `acm` jar file you downloaded.
6. Select `Ok` to add the library.

#### (For Java 24 Only) Enabling Preview Features
1. Open `File -> Project Structure...` again.
2. Select `Pronject` on the left.
3. Under `Language Level`, select `24 (Preview)` or higher.
4. Click `OK` to save the changes.

#### Creating a Karel or ACM program
1. In the `Project` view on the left, right-click on the `src` folder.
2. Select `New -> Java Class`.
3. Name your class and press `Enter`.
4. For Karel, extend `SuperKarel`. For ACM, extend `Program`, `ConsoleProgram`, `DialogueProgram`, or `GraphicsProgram`.

> [!NOTE]
> Currently, you need to override the `main` method in your class for IntelliJ to correctly detect it as a program. The following step might not be necessary in Java 25 or above.

5. Add the following snippet to your class:
```java
    @Override
    public void main(String[] args) {
        super.main(args);
    }
```
You are now ready to write and run your Karel or ACM program.

#### Updating an existing Karel or ACM program

> [!NOTE]
> Currently, you need to override the `main` method in your class for IntelliJ to correctly detect it as a program. The following step might not be necessary in Java 25 or above.

1. Add the following snippet to your class:
```java
    @Override
    public void main(String[] args) {
        super.main(args);
    }
```
