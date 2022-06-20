package model;

import java.util.Objects;

public class Result extends Entity<Long>{
    private User user;
    private Integer result;

    public Result() {
    }

    public Result(User user, Integer result) {
        this.user = user;
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;
        if (!super.equals(o)) return false;
        Result result1 = (Result) o;
        return Objects.equals(user, result1.user) && Objects.equals(result, result1.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, result);
    }

    @Override
    public String toString() {
        return "Result{" +
                "user=" + user +
                ", result=" + result +
                '}';
    }
}
