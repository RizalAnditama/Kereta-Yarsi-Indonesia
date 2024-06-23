package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Muhammad Rizal Anditama Nugraha
 */
public class Passenger {
    
    private int passengerID;
    private String tipeID;
    private String noID;
    private String namaLengkap;
    private String tipeUmur;
    private String kursi;
    
    public Passenger(int passengerID, String namaLengkap, String tipeID, String noID, String kursi) {
        this.passengerID = passengerID;
        this.tipeID = tipeID;
        this.noID = noID;
        this.namaLengkap = namaLengkap;
        this.kursi = kursi;
        if (tipeID == null && noID == null) {
            this.tipeUmur = "Anak-anak";
        } else {
            this.tipeUmur = "Dewasa";
        }
    }
    
    public Passenger(String namaLengkap, String tipeID, String noID, String kursi) {
        this(-1, namaLengkap, tipeID, noID, kursi);
    }

    /**
     * @return the tipeID
     */
    public String getTipeID() {
        return tipeID;
    }

    /**
     * @param tipeID the tipeID to set
     */
    public void setTipeID(String tipeID) {
        this.tipeID = tipeID;
    }

    /**
     * @return the noID
     */
    public String getNoID() {
        return noID;
    }

    /**
     * @param noID the noID to set
     */
    public void setNoID(String noID) {
        this.noID = noID;
    }

    /**
     * @return the namaLengkap
     */
    public String getNamaLengkap() {
        return namaLengkap;
    }

    /**
     * @param namaLengkap the namaLengkap to set
     */
    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    /**
     * @return the tipeUmur
     */
    public String getTipeUmur() {
        return tipeUmur;
    }

    /**
     * @param tipeUmur the tipeUmur to set
     */
    public void setTipeUmur(String tipeUmur) {
        this.tipeUmur = tipeUmur;
    }

    /**
     * @return the kursi
     */
    public String getKursi() {
        return kursi;
    }

    /**
     * @param kursi the kursi to set
     */
    public void setKursi(String kursi) {
        this.kursi = kursi;
    }
    
    public int getPassengerID() {
        return passengerID;
    }
    
    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }
    
}
