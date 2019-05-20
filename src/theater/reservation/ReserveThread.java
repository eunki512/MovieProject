package theater.reservation;

public class ReserveThread extends Thread {
	ReservationMain reservationMain;

	public ReserveThread(ReservationMain reservationMain) {
		this.reservationMain = reservationMain;
	}

	public void run() {
		while (reservationMain.flag_thread) {
			reservationMain.getCinemaNum();
			try {
				sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
