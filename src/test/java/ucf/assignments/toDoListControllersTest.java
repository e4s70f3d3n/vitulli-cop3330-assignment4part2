package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.*;

import java.io.IOException;

import static javafx.scene.control.cell.CheckBoxTableCell.forTableColumn;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.assertions.api.Assertions.assertThat;
import static org.testfx.matcher.control.TableViewMatchers.hasTableCell;

@ExtendWith(ApplicationExtension.class)
class toDoListControllersTest {

    private static Scene scene;
    public static Stage stage1;
    @FXML
    private TableColumn<itemProperties, Boolean> completedColumn;
    @FXML
    private TableColumn<itemProperties, String> descriptionColumn;
    @FXML
    private TableColumn<itemProperties, String> dueDateColumn;

    @FXML
    private TableView<itemProperties> itemList;

    @FXML
    private DatePicker datePicker;


    public ObservableList<itemProperties> toDoList = FXCollections.observableArrayList();

    @AfterEach
    void tearDown() {
    }

    @BeforeEach
    void setUp(FxRobot fxRobot) {
        fxRobot.clickOn("#itemDescriptionTextField");
        fxRobot.write("submit application assignment 1, part 2");
        fxRobot.clickOn(770, 600);
        fxRobot.clickOn(740, 575);
        fxRobot.clickOn("#addItemButton");

        fxRobot.clickOn("#itemDescriptionTextField");
        fxRobot.write("take statistics exam 3");
        fxRobot.clickOn(770, 600);
        fxRobot.clickOn(760, 575);
        fxRobot.clickOn("#addItemButton");

        fxRobot.clickOn("#itemDescriptionTextField");
        fxRobot.write("submit logic project");
        fxRobot.clickOn(770, 600);
        fxRobot.clickOn(795, 575);
        fxRobot.clickOn("#addItemButton");
    }

    private static Parent viewToDoList() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/todolist.fxml"));
        Parent parent = fxmlLoader.load();
        return parent;
    }

    @Start
    void Start(Stage stage) throws IOException {
        scene = new Scene(viewToDoList());
        stage.setScene(scene);
        stage1 = stage;
        stage.show();
    }

    @Test
    void testItemDescriptionTextField(FxRobot fxRobot) {
        fxRobot.clickOn("#itemDescriptionTextField");
        fxRobot.write("submit application assignment 1, part 2");
        verifyThat("#itemDescriptionTextField", TextInputControlMatchers.hasText("submit application assignment 1, part 2"));
    }

    @Test
    void testDatePicker(FxRobot fxRobot) {
        fxRobot.clickOn(770, 600);
        fxRobot.clickOn(755, 390);
        fxRobot.clickOn(675, 500);
    }

    @Test
    void testAddItemButton(FxRobot fxRobot) {
         /*
         test a string value in the description text bar.
         get the actual value from the function.
         set an expected value based on input.
         assert actual and expected equals to each other.
          */

        verifyThat("#itemList", hasTableCell("submit application assignment 1, part 2"));
    }

    @Test
    void testClearListButton(FxRobot fxRobot) {
        fxRobot.clickOn("Clear List");
        fxRobot.sleep(4000);
        verifyThat("#itemList", NodeMatchers.hasChild(""));
    }

    @Test
    void filterItems() {
         /*
        set expected array list of all todolist values.
        get actual array list of all todolist values.
        use assertArrayEquals to verify expected and actual are true.
         */
    }

    @Test
    void checkCompleted(FxRobot fxRobot) {
        /*
        set expected array of completed todolist values.
        get actual values that start as uncompleted values but
        should be marked as completed if the function works properly.
        use assertArrayEquals to determine if actual equals expected.
         */

        fxRobot.clickOn(450, 228);
        fxRobot.sleep(4000);
        verifyThat("#itemList", TableViewMatchers.containsRow(true, "submit application assignment 1, part 2", "2021-12-07"));
    }

    @Test
    void should_NotAddItem_when_GreaterThan256Characters(FxRobot fxRobot) {

        fxRobot.clickOn("#itemDescriptionTextField");
        fxRobot.write("submit application assignment 1, part 2, submit practice exercises in c++, submit application assignment in c++ (EXTRA " +
                "CREDIT), submit computer logic and organization semester project, complete statistics exam 3,  submit statistics homework 5, " +
                "complete statistics final exam");
        fxRobot.clickOn(770, 600);
        fxRobot.clickOn(740, 575);
        fxRobot.clickOn("#addItemButton");
        fxRobot.sleep(4000);
        verifyThat("#itemList", TableViewMatchers.hasNumRows(3));

    }

    @Test
    void should_NotAddItem_when_LessThan1Character(FxRobot fxRobot) {

        fxRobot.clickOn("#itemDescriptionTextField");
        fxRobot.write("");
        fxRobot.clickOn(770, 600);
        fxRobot.clickOn(740, 575);
        fxRobot.clickOn("#addItemButton");
        fxRobot.sleep(4000);
        verifyThat("#itemList", TableViewMatchers.hasNumRows(3));

    }

    @Test
    void should_NotAddItem_when_DueDateIsNotValid(FxRobot fxRobot) {
        fxRobot.clickOn("#itemDescriptionTextField");
        fxRobot.write("");
        fxRobot.clickOn(770, 600);
        fxRobot.clickOn(725, 450);
        fxRobot.clickOn("#addItemButton");
        fxRobot.sleep(4000);
        verifyThat("#itemList", TableViewMatchers.hasNumRows(3));
    }

    @Test
    void removeItem(FxRobot fxRobot) {
        /*
        test an empty string value in the description text bar.
        get the actual value from the function.
        assert actual and expected equals to each other.
         */

        fxRobot.clickOn(770, 225);
        fxRobot.clickOn("#removeItemButton");
        fxRobot.sleep(4000);
        verifyThat("#itemList", TableViewMatchers.hasNumRows(2));
    }


    @Test
    void should_AllowItemDescriptionCellContentEditing_when_TableViewCellIsDoubleClicked(FxRobot fxRobot) {

        /*
        verify that all three items are contained within the table view:
        the first row with an uncompleted item titled "submit application assignment 1, part 2" due
        on "2021-12-07"
        the second row with an uncompleted item titled "take statistics exam 3" due on "2021-12-08"
        the third row with an uncompleted item titled "submit logic project" due on "2021-12-09"
        double click on the item description column cell containing the string "submit logic project"
        erase the old text
        write new string "submit final draft of logic project"
        enter new string value.
        verify that the table view now contains a third row with an uncompleted item titled "submit
        final draft logic project" due on "2021-12-09"
        */

        verifyThat("#itemList", TableViewMatchers.containsRow(false, "submit application assignment 1, part 2", "2021-12-07"));
        verifyThat("#itemList", TableViewMatchers.containsRow(false, "take statistics exam 3", "2021-12-08"));
        verifyThat("#itemList", TableViewMatchers.containsRow(false, "submit logic project", "2021-12-09"));
        fxRobot.doubleClickOn("submit logic project");
        fxRobot.eraseText(20);
        fxRobot.write("submit final draft of logic project");
        fxRobot.press(KeyCode.ENTER);
        fxRobot.sleep(4000);
        verifyThat("#itemList", TableViewMatchers.containsRow(false, "submit final draft of logic project", "2021-12-09"));

    }

    @Test
    void should_AllowDueDateCellContentEditing_when_TableViewCellIsDoubleClicked(FxRobot fxRobot) {

        /*
        verify that all three items are contained within the table view:
        the first row with an uncompleted item titled "submit application assignment 1, part 2" due
        on "2021-12-07"
        the second row with an uncompleted item titled "take statistics exam 3" due on "2021-12-08"
        the third row with an uncompleted item titled "submit logic project" due on "2021-12-09"
        double click on the due date column cell containing the string "2021-12-08"
        erase the old text
        write new string "2021-12-13"
        enter new string value.
        verify that the table view now contains a third row with an uncompleted item titled "take
        statistics exam 3" due on "2021-12-13"
        */

        verifyThat("#itemList", TableViewMatchers.containsRow(false, "submit application assignment 1, part 2", "2021-12-07"));
        verifyThat("#itemList", TableViewMatchers.containsRow(false, "take statistics exam 3", "2021-12-08"));
        verifyThat("#itemList", TableViewMatchers.containsRow(false, "submit logic project", "2021-12-09"));
        fxRobot.doubleClickOn("2021-12-08");
        fxRobot.eraseText(10);
        fxRobot.write("2021-12-13");
        fxRobot.press(KeyCode.ENTER);
        fxRobot.sleep(4000);
        verifyThat("#itemList", TableViewMatchers.containsRow(false, "take statistics exam 3", "2021-12-13"));

    }





    @Test
    void showCompletedFilter(FxRobot fxRobot) {
    /*
    click on check boxes for first and third table view items.
    click on filter items button.
    click on show completed items button.
    verify that the table view only contains two rows now instead of the previous 3.
    verify that the table view contains a row with a completed item titled "submit
    application assignment 1, part 2" due on "2021-12-07"
    verify that the table view contains a row with a completed item titled "submit
    logic project" due on "2021-12-09"
     */

        fxRobot.clickOn(455, 228);
        fxRobot.clickOn(455, 283);
        fxRobot.clickOn("Filter Items");
        fxRobot.clickOn("#showCompletedMenuButton");
        fxRobot.sleep(4000);
        verifyThat("#itemList", TableViewMatchers.hasNumRows(2));
        verifyThat("#itemList", TableViewMatchers.containsRow(true, "submit application assignment 1, part 2", "2021-12-07"));
        verifyThat("#itemList", TableViewMatchers.containsRow(true, "submit logic project", "2021-12-09"));

    }

    @Test
    void showUncompletedFilter(FxRobot fxRobot) {

         /*
        click on check boxes for first and third table view items.
        click on filter items button.
        click on show uncompleted items button.
        verify that the table view only contains one rows now instead of the previous 3.
        verify that the table view contains a row with an uncompleted item titled "take
        statistics exam 3" due on "2021-12-08"
         */

        fxRobot.clickOn(455, 228);
        fxRobot.clickOn(455, 283);
        fxRobot.clickOn("Filter Items");
        fxRobot.clickOn("#showUncompletedMenuButton");
        fxRobot.sleep(4000);
        verifyThat("#itemList", TableViewMatchers.hasNumRows(1));
        verifyThat("#itemList", TableViewMatchers.containsRow(false, "take statistics exam 3", "2021-12-08"));

    }

    @Test
    void should_VerifyEntireListContentsPresent_when_SwitchingFromCompletedFilter(FxRobot fxRobot) {
        /*
        click on check boxes for first and third table view items.
        click on filter items button.
        click on show completed items button.
        verify that the table view only contains two rows now instead of the previous 3.
        click on show entire list button.
        verify that the table view now contains all 3 original rows.
        */

        fxRobot.clickOn(455, 228);
        fxRobot.clickOn(455, 283);
        fxRobot.clickOn("Filter Items");
        fxRobot.clickOn("#showCompletedMenuButton");
        verifyThat("#itemList", TableViewMatchers.hasNumRows(2));
        fxRobot.clickOn("#showEntireListButton");
        fxRobot.sleep(4000);
        verifyThat("#itemList", TableViewMatchers.hasNumRows(3));
        verifyThat("#itemList", TableViewMatchers.containsRow(true, "submit application assignment 1, part 2", "2021-12-07"));
        verifyThat("#itemList", TableViewMatchers.containsRow(false, "take statistics exam 3", "2021-12-08"));
        verifyThat("#itemList", TableViewMatchers.containsRow(true, "submit logic project", "2021-12-09"));
    }

    @Test
    void should_VerifyEntireListContentsPresent_when_SwitchingFromUncompletedFilter(FxRobot fxRobot) {
        /*
        click on check boxes for first and third table view items.
        click on filter items button.
        click on show uncompleted items button.
        verify that the table view only contains one rows now instead of the previous 3.
        click on show entire list button.
        verify that the table view now contains all 3 original rows.
         */

        fxRobot.clickOn(455, 228);
        fxRobot.clickOn(455, 283);
        fxRobot.clickOn("Filter Items");
        fxRobot.clickOn("#showUncompletedMenuButton");
        verifyThat("#itemList", TableViewMatchers.hasNumRows(1));
        fxRobot.clickOn("#showEntireListButton");
        fxRobot.sleep(4000);
        verifyThat("#itemList", TableViewMatchers.hasNumRows(3));
        verifyThat("#itemList", TableViewMatchers.containsRow(true, "submit application assignment 1, part 2", "2021-12-07"));
        verifyThat("#itemList", TableViewMatchers.containsRow(false, "take statistics exam 3", "2021-12-08"));
        verifyThat("#itemList", TableViewMatchers.containsRow(true, "submit logic project", "2021-12-09"));
    }

    @Test
    void sortByDueDateAscendingFilter(FxRobot fxRobot) {

         /*
        add two new items.
        one with due date 2021-12-18 and the other 2021-12-12
        click on filter items button.
        click on sort by due date ascending button.
        verify that each of the five items have the proper associated boolean value,
        text, and due date and use at index to organize by ascending date
         */

        fxRobot.clickOn("#itemDescriptionTextField");
        fxRobot.write("submit practice exercises in c++");
        fxRobot.clickOn(770, 600);
        fxRobot.clickOn(755, 390);
        fxRobot.clickOn(850, 500);
        fxRobot.clickOn("#addItemButton");

        fxRobot.clickOn("#itemDescriptionTextField");
        fxRobot.write("submit application assignment in c++ (EXTRA CREDIT)");
        fxRobot.clickOn(770, 600);
        fxRobot.clickOn(755, 390);
        fxRobot.clickOn(675, 500);
        fxRobot.clickOn("#addItemButton");

        fxRobot.clickOn("Filter Items");
        fxRobot.clickOn("#sortByDueDateAscendingMenuButton");
        fxRobot.sleep(4000);
        verifyThat("#itemList", TableViewMatchers.containsRowAtIndex(0, false, "submit application assignment 1, part 2", "2021-12-07"));
        verifyThat("#itemList", TableViewMatchers.containsRowAtIndex(1, false, "take statistics exam 3", "2021-12-08"));
        verifyThat("#itemList", TableViewMatchers.containsRowAtIndex(2, false, "submit logic project", "2021-12-09"));
        verifyThat("#itemList", TableViewMatchers.containsRowAtIndex(4, false, "submit practice exercises in c++", "2021-12-18"));
        verifyThat("#itemList", TableViewMatchers.containsRowAtIndex(3, false, "submit application assignment in c++ (EXTRA CREDIT)", "2021-12-12"));

    }

    @Test
    void sortByDueDateDescendingFilter() {
        /*
        add two new items.
        one with due date 2021-12-18 and the other 2021-12-12
        click on filter items button.
        click on sort by due date ascending button.
        verify that each of the five items have the proper associated boolean value,
        text, and due date and use at index to organize by descending date
         */


    }
}




