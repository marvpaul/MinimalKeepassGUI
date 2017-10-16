package model;

import org.linguafranca.pwdb.Database;
import org.linguafranca.pwdb.kdbx.KdbxCreds;
import org.linguafranca.pwdb.kdbx.simple.SimpleDatabase;
import view.FXWindow;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class DatabaseModel {
    //Here we store some data
    public Database database;
    private List<String> entries;
    private String path;
    private FXWindow view;

    /**
     * Load a certain database
     * TODO: Add a dialogue to ask for file + password
     * @param view
     */
    public DatabaseModel(FXWindow view) {
        this.view = view;
        path = view.askForPath();
        view.askForPassword();

    }

    /**
     * Search in database
     * @param searchQuery a query which is used to search in all entries
     */
    public void searchInDatabase(String searchQuery){
        entries = database.findEntries(searchQuery);
    }

    /**
     * Getter :)
     * @return all the database entries
     */
    public List<String> getEntries() {
        return entries;
    }

    private void openDatabase(String path, KdbxCreds pass){

        //Try to read database
        try {
            InputStream inputStream = new FileInputStream(String.valueOf(path));
            database = SimpleDatabase.load(pass, inputStream);
        } catch (Exception e) {
            System.err.println("Failed to open DatabaseModel!");
        }
    }

    public void applyPass(String password){
        //Pass
        KdbxCreds pass = new KdbxCreds(password.getBytes());

        openDatabase(path, pass);

        //Just show all entries
        searchInDatabase("");

        //View should show the loaded entries
        view.setList(getEntries());
    }
}
