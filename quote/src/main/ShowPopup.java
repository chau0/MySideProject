package main;

public class ShowPopup {
	public static void main(String[] args) {
		DatabaseConnector databaseConnector = new DatabaseConnector("quote_crawler");
		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Quote quote = databaseConnector.getRandomQuote();
					NotificationBox notificationBox = new NotificationBox();
					notificationBox.create(quote.getContent(), quote.getAuthor(), 50000);
					Utils.log(quote.getContent());
					try {
						Thread.sleep(1200000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		thread.start();

	}
}
