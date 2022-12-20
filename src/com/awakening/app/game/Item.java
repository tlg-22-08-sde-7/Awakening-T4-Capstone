package com.awakening.app.game;


import java.util.ArrayList;
import java.util.List;

public class Item {

    private Item.FrontDoorKey frontDoorKey;
    private Item.Journal journal;
    private Item.Batteries batteries;
    private Item.PatientFile patientFile;
    private Item.Bandages bandages;
    private Item.PressPass pressPass;
    private Item.CellPhone cellPhone;
    private Item.PaperClip paperClip;
    private Item.Camera camera;
    private Item.Table table;
    private Item.Desk desk;



    public List<ItemsSetup> loadItems(){
        List<Item.ItemsSetup> itemList = new ArrayList<>();
        itemList.add(frontDoorKey);
        itemList.add(journal);
        itemList.add(batteries);
        itemList.add(patientFile);
        itemList.add(bandages);
        itemList.add(pressPass);
        itemList.add(cellPhone);
        itemList.add(paperClip);
        itemList.add(camera);
        itemList.add(table);
        itemList.add(desk);
        return itemList;
    }






    public ItemsSetup findItem(String itemName){
        if(itemName.equals(frontDoorKey.getName())){
            return frontDoorKey;
        }
        if(itemName.equals(journal.getName())){
            return journal;
        }
        if(itemName.equals(batteries.getName())){
            return batteries;
        }
        if(itemName.equals(patientFile.getName())){
            return patientFile;
        }
        if(itemName.equals(bandages.getName())){
            return bandages;
        }
        if(itemName.equals(pressPass.getName())){
            return pressPass;
        }
        if(itemName.equals(cellPhone.getName())){
            return cellPhone;
        }
        if(itemName.equals(paperClip.getName())){
            return paperClip;
        }
        if(itemName.equals(camera.getName())){
            return camera;
        }
        if(itemName.equals(table.getName())) {
            return table;
        }
        if(itemName.equals(desk.getName())) {
            return desk;
        }
        else
            return null;
    }

    public Item.FrontDoorKey getFrontDoorKey() {
        return frontDoorKey;
    }

    public void setFrontDoorKey(Item.FrontDoorKey frontDoorKey) {
        this.frontDoorKey = frontDoorKey;
    }

    public Item.Journal getJournal() {
        return journal;
    }

    public void setJournal(Item.Journal journal) {
        this.journal = journal;
    }

    public Item.Batteries getBatteries() {
        return batteries;
    }

    public void setBatteries(Item.Batteries batteries) {
        this.batteries = batteries;
    }

    public Item.PatientFile getPatientFile() {
        return patientFile;
    }

    public void setPatientFile(Item.PatientFile patientFile) {
        this.patientFile = patientFile;
    }

    public Item.Bandages getBandages() {
        return bandages;
    }

    public void setBandages(Item.Bandages bandages) {
        this.bandages = bandages;
    }

    public Item.PressPass getPressPass() {
        return pressPass;
    }

    public void setPressPass(Item.PressPass pressPass) {
        this.pressPass = pressPass;
    }

    public Item.CellPhone getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(Item.CellPhone cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Item.PaperClip getPaperClip() {
        return paperClip;
    }

    public void setPaperClip(Item.PaperClip paperClip) {
        this.paperClip = paperClip;
    }

    public Item.Camera getCamera() {
        return camera;
    }

    public void setCamera(Item.Camera camera) {
        this.camera = camera;
    }

    public Item.Table getTable() {
        return table;
    }

    public void setTable (Item.Table table) {
        this.table = table;
    }

    public void setDesk (Item.Desk desk) {
        this.desk = desk;
    }

    public Item.Desk getDesk() {
        return desk;
    }

    public static class ItemsSetup{
        private String name;
        private String description;
        private String startLocation;
        private boolean specialItemFlag;
        private int charge;
        private int attackPoints;

        public ItemsSetup(){ }



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStartLocation() {
            return startLocation;
        }

        public void setStartLocation(String startLocation) {
            this.startLocation = startLocation;
        }

        public boolean isSpecialItemFlag() {
            return specialItemFlag;
        }

        public void setSpecialItemFlag(boolean specialItemFlag) {
            this.specialItemFlag = specialItemFlag;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public int getAttackPoints() {
            return attackPoints;
        }

        public void setAttackPoints(int attackPoints) {
            this.attackPoints = attackPoints;
        }
    }




//Creation of special classes

    public static class FrontDoorKey extends Item.ItemsSetup {
        public FrontDoorKey() {
        }
    }

    public  static class Journal extends Item.ItemsSetup {
        public Journal() {
        }
    }

    public  static class Batteries extends Item.ItemsSetup {
        public Batteries() {
        }
    }

    public  static class PatientFile extends Item.ItemsSetup {
        public PatientFile() {
        }
    }

    public  static class  Bandages extends Item.ItemsSetup {
        public Bandages() {
        }
    }

    public  static class PressPass extends Item.ItemsSetup {
        public PressPass() {
        }
    }

    public  static class CellPhone extends Item.ItemsSetup {
        public CellPhone() {
        }
    }

    public  static class PaperClip extends Item.ItemsSetup {
        public PaperClip() {
        }
    }

    public  static class Camera extends Item.ItemsSetup {
        public Camera() {
        }
    }

    public static class Table extends Item.ItemsSetup {
        public Table() {
        }
    }

    public static class Desk extends Item.ItemsSetup {
        public Desk() {
        }
    }

}
