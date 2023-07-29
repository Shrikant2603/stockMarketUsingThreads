package com.project.sharemarket;

public class StockGameMain {

    public static void main(String[] args) {
	// TODO Auto-generated method stub
	try {
	    StockGame[] stockTraders = { new StockGame("Xander",
		    "C:\\Users\\shrik\\eclipse-workspace\\stockmarketusingthreads\\bin\\com\\project\\sharemarket\\TraderOneMoves.txt"),
		    new StockGame("Afua",
			    "C:\\Users\\shrik\\eclipse-workspace\\stockmarketusingthreads\\bin\\com\\project\\sharemarket\\TraderTwoMoves.txt") };
	    for (int i = 0; i < stockTraders.length; i++) {
		stockTraders[i].start();
	    }
	    for (int i = 0; i < stockTraders.length; i++) {
		stockTraders[i].join();
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	    return;
	}
    }

}
