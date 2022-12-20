package com.awakening.app.game;

import java.util.HashMap;
import java.util.List;

public class RoomMap {

    private Basement basement;
    private Morgue morgue;
    private FilingCabinet filingCabinet;
    private EmergencyRoom emergencyRoom;
    private Office office;
    private Desk desk;
    private FrontDesk frontDesk;
    private Hallway hallway;
    private KeyPad keyPad;
    private PatientRoom patientRoom;

    public Basement getBasement() {
        return basement;
    }

    public void setBasement(Basement basement) {
        this.basement = basement;
    }

    public Morgue getMorgue() {
        return morgue;
    }

    public void setMorgue(Morgue morgue) {
        this.morgue = morgue;
    }

    public FilingCabinet getFilingCabinet() {
        return filingCabinet;
    }

    public void setFilingCabinet(FilingCabinet filingCabinet) {
        this.filingCabinet = filingCabinet;
    }

    public EmergencyRoom getEmergencyRoom() {
        return emergencyRoom;
    }

    public void setEmergencyRoom(EmergencyRoom emergencyRoom) {
        this.emergencyRoom = emergencyRoom;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Desk getDesk() {
        return desk;
    }

    public void setDesk(Desk desk) {
        this.desk = desk;
    }

    public FrontDesk getFrontDesk() {
        return frontDesk;
    }

    public void setFrontDesk(FrontDesk frontDesk) {
        this.frontDesk = frontDesk;
    }

    public Hallway getHallway() {
        return hallway;
    }

    public void setHallway(Hallway hallway) {
        this.hallway = hallway;
    }

    public KeyPad getKeypad() {
        return keyPad;
    }

    public void setKeypad(KeyPad keypad) {
        this.keyPad = keypad;
    }

    public PatientRoom getPatientRoom() {
        return patientRoom;
    }

    public void setPatientRoom(PatientRoom patientRoom) {
        this.patientRoom = patientRoom;
    }

    public RoomLayout getRoom(String name) {
        if (name == null) {
            return null;
        }
        switch (name) {
            case "Basement":
                return basement;
            case "Morgue":
                return morgue;
            case "Filing Cabinet":
                return filingCabinet;
            case "Emergency Room":
                return emergencyRoom;
            case "Office":
                return office;
            case "Desk":
                return desk;
            case "Front Desk":
                return frontDesk;
            case "Hallway":
                return hallway;
            case "Key Pad":
                return keyPad;
            case "Patient Room":
                return patientRoom;
            default:
                return null;
        }
    }

    public static class RoomLayout {
        private String name;
        private HashMap<String, String> directions;
        private String description;
        private boolean isLocked;
        private List<String> items;
        private String npcName;

        public RoomLayout() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public HashMap<String, String> getDirections() {
            return directions;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isLocked() {
            return isLocked;
        }

        public void setLocked(boolean locked) {
            isLocked = locked;
        }

        public List<String> getItems() {
            return items;
        }

        public void setItems(List<String> items) {
            this.items = items;
        }

        public String getNpcName() {
            return npcName;
        }

        public void setNpcName(String npcName) {
            this.npcName = npcName;
        }

        public void addItem(String item) {
            items.add(item);
        }
    }

    public static class Basement extends RoomLayout {
        public Basement() {
        }

    }

    public static class Morgue extends RoomLayout {
        public Morgue() {
        }
    }

    public  static class FilingCabinet extends RoomLayout {
        public FilingCabinet() {
        }
    }

    public static class EmergencyRoom extends RoomLayout {
        public EmergencyRoom() {
        }
    }

    public static class Office extends RoomLayout {
        public Office() {
        }
    }

    public static class Desk extends RoomLayout {
        public Desk() {
        }
    }

    public static class FrontDesk extends RoomLayout {
        public FrontDesk() {
        }
    }

    public static class Hallway extends RoomLayout {
        public Hallway() {
        }
    }

    public static class KeyPad extends RoomLayout {
        public KeyPad() {
        }
    }

    public static class PatientRoom extends RoomLayout {
        public PatientRoom() {
        }
    }
}


