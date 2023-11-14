package christmas.controller;

import christmas.domain.dto.ConfirmedReservation;
import christmas.domain.reservation.Reservation;
import christmas.view.InputView;
import christmas.view.OutputView;

public class ReservationController {
    private final InputView inputView;
    private final OutputView outputView;

    public ReservationController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Reservation reservation = inputView.inputReservation();
        ConfirmedReservation confirmedReservation = applyEvents(reservation);
        outputView.printBenefitEvents(confirmedReservation);
    }

    private ConfirmedReservation applyEvents(Reservation reservation) {
        reservation.applyEvents();
        return reservation.toConfirmedReservation();
    }
}
