/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TARUC
 */
public class Earning {

    private String invoiceNo;
    private int value;
    private String memberNo;
    private boolean redeemed;
    private LocalDate earningDate;
    private LocalDate expiryDate;

    public Earning() {

    }

    public Earning(String invoiceNo, int value, String memberNo, boolean redeemed, LocalDate earningDate, LocalDate expiryDate) {
        this.invoiceNo = invoiceNo;
        this.value = value;
        this.memberNo = memberNo;
        this.redeemed = redeemed;
        this.earningDate = earningDate;
        this.expiryDate = expiryDate;
    }

    public Earning(String invoiceNo, int value, String memberNo) {
        this.invoiceNo = invoiceNo;
        this.value = value;
        this.memberNo = memberNo;
        this.earningDate = LocalDate.now();
        this.expiryDate = LocalDate.now();
        this.redeemed = false;

        // write to file logic
        writeToFile();
    }

    private void writeToFile() {
        try {
//            CSVWrite csvWrite = new CSVWrite();
            List<String[]> dataLines;
            dataLines = new ArrayList<>();
            dataLines.add(new String[]{this.getMemberNo(), this.getInvoiceNo(), Integer.toString(this.getValue()), String.valueOf(this.isRedeemed()), this.getEarningDate().toString(), this.getExpiryDate().toString()});

            CSVWrite.givenDataArray_whenConvertToCSV_thenOutputCreated(dataLines,"earning.csv");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public boolean isRedeemed() {
        return redeemed;
    }

    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }

    public LocalDate getEarningDate() {
        return earningDate;
    }

    public void setEarningDate(LocalDate earningDate) {
        this.earningDate = earningDate;
    }

}
