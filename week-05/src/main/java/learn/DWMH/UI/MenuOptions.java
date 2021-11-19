package learn.DWMH.UI;

public enum MenuOptions {
    Exit(0,"Exit"),
    View_Reservations(1,"View Reservation for Host"),
    Make_Reservation(2,"Make a Reservation"),
    Edit_Reservation(3,"Edit a Reservation"),
    Cancel_Reservation(4,"Cancel a Reservation")
    ;


    private int value;
    private String message;

    private MenuOptions(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public static MenuOptions fromValue(int value) {
        for (MenuOptions option : MenuOptions.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return Exit;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

}
