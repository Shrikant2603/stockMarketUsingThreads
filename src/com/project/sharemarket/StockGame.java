package com.project.sharemarket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockGame extends Thread {
    private static double stockPrices = 100;
    private static int availableShares = 100;
    private static int tradeCount = 1;
    private static final Object lock = new Object();
    private String name;
    private int numShares;
    private String fileName;
    private List<String> operations = new ArrayList<>();

    public StockGame(String name, String fileName) {
	super();
	this.name = name;
	this.fileName = fileName;
	this.numShares = 0;
    }

    public synchronized static double getStockPrices() {
	return stockPrices;
    }

    public synchronized static void setStockPrices(double stockPrices) {
	StockGame.stockPrices = stockPrices;
    }

    public synchronized static int getAvailableShares() {
	return availableShares;
    }

    public synchronized static void setAvailableShares(int availableShares) {
	StockGame.availableShares = availableShares;
    }

    public synchronized static int getTradeCount() {
	return tradeCount;
    }

    public synchronized static void setTradeCount(int tradeCount) {
	StockGame.tradeCount = tradeCount;
    }

    public String getTraderName() {
	return name;
    }

    public void setTraderName(String name) {
	this.name = name;
    }

    public int getNumShares() {
	return numShares;
    }

    public void setNumShares(int numShares) {
	this.numShares = numShares;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public List<String> getOperations() {
	return operations;
    }

    public void setOperations(List<String> operations) {
	this.operations = operations;
    }

    public void run() {
	readTradeFile();
	makeOperations();
    }

    private void makeOperations() {
	for (int i = 0; i < operations.size(); i++) {
	    String[] arr = operations.get(i).split(",");
	    String op = arr[0].trim();
	    int shares = Integer.parseInt(arr[1]);

	    if (!op.equals("BUY") && !op.equals("SELL") || shares < 0) {
		System.out.println("Error, invalid input!");
		continue;
	    } else {
		synchronized (lock) {
		    System.out.println("-------------------------------");
		    System.out.println("Stock Price: " + getStockPrices());
		    System.out.println("Available Shares: " + getAvailableShares());

		    if (op.equals("BUY")) {
			if (shares <= availableShares) {
			    System.out.println("Trade number: " + getTradeCount());
			    System.out.println("Name: " + getTraderName());
			    System.out.println("Purchasing " + shares + " shares at " + getStockPrices() + "...");
			    this.setNumShares(this.getNumShares() + shares);
			    setAvailableShares(getAvailableShares() - shares);
			    setStockPrices(getStockPrices() + (1.5 * shares));
			    tradeCount++;
			} else {
			    System.out.println("Insufficient shares available, cancelling order...");
			}
		    } else {
			if (shares <= getNumShares()) {
			    System.out.println("Trade number: " + getTradeCount());
			    System.out.println("Name: " + getTraderName());
			    System.out.println("Selling " + shares + " shares at " + getStockPrices() + "...");
			    this.setNumShares(this.getNumShares() - shares);
			    setAvailableShares(getAvailableShares() + shares);
			    setStockPrices(getStockPrices() - (2 * shares));
			    tradeCount++;
			} else {
			    System.out.println("Insufficient owned shares, cancelling order...");
			}
		    }
		}
	    }
	}
    }

    private void readTradeFile() {
	try {
	    BufferedReader reader = new BufferedReader(new FileReader(fileName));
	    String line;
	    while ((line = reader.readLine()) != null) {
		operations.add(line);
	    }
	} catch (IOException e) {
	    System.out.println("Error No: Trades found!");
	}
    }
}
