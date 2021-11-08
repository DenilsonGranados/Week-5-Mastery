# Week-5-Mastery

* Should have tests for GuestService and HostService to test FindByLastName.
* Please show a reservation summary, including total cost, and give user a chance to confirm
* Please give user a chance to confirm when deleting a reservation.

Some issues with the edit reservation process:

List of reservations includes 0, which should exit but doesn't:
```
13: 2021-11-09, 2021-11-10
14: 2021-11-11, 2021-11-15
0: Exit
Select a reservation by their index [0-14]: 0
Start date in the form of yyyy-MM-dd: 
```

Host with no reservations incorrectly asks for start date:
```
Edit a Reservation
==================
Host last name starts with: Fader
1: mfader2@amazon.co.jp, Fader
0: Exit
Select a host by their index [0-1]: 1
No reservations found
Start date in the form of yyyy-MM-dd: 
```


14: 2021-11-11, 2021-11-15
0: Exit
Select a reservation by their index [0-14]: 14In this case, I just want to extend the reservation one day, but the validation compares the dates to itself and rejects it:
```
Start date in the form of yyyy-MM-dd: 2021-11-11
End date in the form of yyyy-MM-dd: 2021-11-16

Error
=====
This reservation and reservation 14 dates overlap
```

## Possible Improvements

* Better to sort by date when displaying reservations. It's easier to see where the open dates are.
* Not necessary to show past reservations when making, editing or cancelling reservations.
* You still have the word "Forage" in your messages.
