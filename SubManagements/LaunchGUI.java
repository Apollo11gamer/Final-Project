public void initiateLaunch() {
    launchButton.setEnabled(false);
    
    for (int i = 10; i >= 0; i--) {
        countdownLabel.setText("T-" + i + " seconds...");
        sleep(1000);
    }

    countdownLabel.setText("Launch!");
    statusLabel.setText("Rocket is ascending...");

    playSound("SubManagements/Accend.mp3"); // Start playing ascent sound

    long spacewalkStartTime = 0;

    while (currentFuel > 0 && currentAltitude < SPACEWALK_ALTITUDE) {
        double acceleration = (THRUST / ROCKET_MASS) - GRAVITY;
        currentSpeed += acceleration;
        currentAltitude += currentSpeed;
        currentFuel -= FUEL_BURN_RATE;

        fuelLabel.setText("Fuel: " + String.format("%.2f", currentFuel) + " lbs");
        speedLabel.setText("Speed: " + String.format("%.2f", currentSpeed) + " m/s");
        altitudeLabel.setText("Altitude: " + String.format("%.2f", currentAltitude) + " m");

        sleep(500);
    }

    stopSound(); // Stop ascent sound

    statusLabel.setText("Spacewalk started! 30 sec remaining...");
    playSound("SubManagements/InSpace.mp3"); // Start playing spacewalk sound

    spacewalkStartTime = System.nanoTime();

    while ((System.nanoTime() - spacewalkStartTime) / 1_000_000_000 < 30) {
        long timeRemaining = 30 - ((System.nanoTime() - spacewalkStartTime) / 1_000_000_000);
        countdownLabel.setText("Spacewalk: " + timeRemaining + " sec left");
        sleep(1000);
    }

    stopSound(); // Stop spacewalk sound

    statusLabel.setText("Spacewalk complete. Beginning descent.");
    descend();
}