package model;

import org.linguafranca.pwdb.Database;
import org.linguafranca.pwdb.kdbx.KdbxCreds;
import org.linguafranca.pwdb.kdbx.simple.SimpleDatabase;
import org.linguafranca.pwdb.kdbx.simple.SimpleEntry;
import org.linguafranca.pwdb.kdbx.simple.SimpleGroup;
import view.FXWindow;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseModel {
    //Here we store some data
    public Database database;
    private List<SimpleEntry> entries;
    public List<String> stringList;
    private String path;
    private FXWindow view;
    private List<SimpleGroup> groups;
    private SimpleGroup actualGroup;
    private SimpleEntry actualEntry;

    /**
     * Load a certain database
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
    public List<SimpleEntry> getEntries() {
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

        openGroup((SimpleGroup)database.getRootGroup());

        convertSelectionToStringList();

        //View should show the loaded entries
        view.setList(stringList);
    }

    public void convertSelectionToStringList(){

        stringList = new ArrayList<>();

        for(SimpleGroup group : groups){
            stringList.add(group.getName());
        }

        for(SimpleEntry entry : entries){
            stringList.add(entry.getTitle());
        }


    }

    public void open(int index){
        if(groups.size() > index){
            openGroup(groups.get(index));
            convertSelectionToStringList();

            //View should show the loaded entries
            view.setList(stringList);
        } else{
            view.showEntry(entries.get(index-groups.size()));
        }
    }

    public void goUp(){
        openGroup(actualGroup.getParent());

        convertSelectionToStringList();

        view.setList(stringList);
    }

    public void openGroup(SimpleGroup groupToEnter){
        //Just find all entries
        entries = groupToEnter.getEntries();

        groups = groupToEnter.getGroups();
        actualGroup = groupToEnter;
    }


}
