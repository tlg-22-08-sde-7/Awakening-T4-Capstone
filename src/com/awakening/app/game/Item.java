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

    public Item.CellPhone getCellPhone() {
        return cellPhone;
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

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
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
