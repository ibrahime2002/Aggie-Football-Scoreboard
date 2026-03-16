package model;

public final class ScoreboardTests {
    public static void main(String[] args) {
        Scoreboard sb = new Scoreboard();
        int fails = 0;

        try {
            // cannot score before names set
            try {
                sb.addTouchdownHome();
                System.out.println("FAIL: allowed scoring before names set");
                fails++;
            } catch (IllegalStateException expected) {
                System.out.println("PASS: prevented scoring before names set");
            }

            // blank name check
            try {
                sb.setTeamNames("  ", "Away");
                System.out.println("FAIL: blank home name allowed");
                fails++;
            } catch (IllegalArgumentException ok) {
                System.out.println("PASS: blank home name rejected");
            }

            sb.setTeamNames("Aggies", "Gamecocks");
            sb.addTouchdownHome();
            if (sb.getHomeScore() != 6) {
                System.out.println("FAIL: home score should be 6");
                fails++;
            } else System.out.println("PASS: home score updated");

            sb.addFieldGoalAway();
            if (sb.getAwayScore() != 3) {
                System.out.println("FAIL: away score should be 3");
                fails++;
            } else System.out.println("PASS: away score updated");

            sb.undoLast();
            if (sb.getAwayScore() != 0) {
                System.out.println("FAIL: undo did not revert away score");
                fails++;
            } else System.out.println("PASS: undo reverted away score");

            try {
                sb.clearGame();
                if (sb.getHomeScore() != 0 || sb.getAwayScore() != 0) {
                    System.out.println("FAIL: clear did not reset scores");
                    fails++;
                } else System.out.println("PASS: clear reset scores");

                try {
                    sb.undoLast();
                    System.out.println("FAIL: undo after clear should throw");
                    fails++;
                } catch (IllegalStateException expected) {
                    System.out.println("PASS: undo after clear threw as expected");
                }

            } catch (Throwable t) {
                System.out.println("FAIL: unexpected error: " + t.getMessage());
                fails++;
            }

        } catch (Throwable t) {
            System.out.println("FAIL: unexpected exception: " + t.getMessage());
            fails++;
        }

        if (fails == 0) System.out.println("ALL TESTS PASS");
        else System.out.println("TESTS FAILED: " + fails + " failures");
    }
}
