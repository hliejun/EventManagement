package sg.edu.nus.comp.orbital.eventmanagement;

/**
 * Created by Larry on 21/6/15.
 */

public class UserCostPair {
    User user = null;
    Double cost = null;

    public UserCostPair(User user, Double cost) {
        this.user = user;
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public Double getCost() {
        return cost;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
