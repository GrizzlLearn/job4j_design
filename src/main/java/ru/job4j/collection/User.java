package ru.job4j.collection;

import java.util.*;

public class User {
    private String name;
    private int children;
    private Calendar birthday;

    User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    public static void main(String[] args) {
        Calendar birth = Calendar.getInstance();
        User user1 = new User("Slava", 1, birth);
        User user2 = new User("Slava", 1, birth);
        Map<User, Object> map = new HashMap<>(16);
        map.put(user1, new Object());
        map.put(user2, new Object());

        int hashcode1 = user1.hashCode();
        int hash1 = hashcode1 ^ (hashcode1 >>> 16);
        int bucket1 = hash1 & 15;

        int hashcode2 = user2.hashCode();
        int hash2 = hashcode2 ^ (hashcode1 >>> 16);
        int bucket2 = hash2 & 15;

        System.out.printf("user1 = хэшкод: %s, хэш: %s, backet: %s", hashcode1, hash1, bucket1);
        System.out.println(System.lineSeparator());
        System.out.printf("user2 = хэшкод: %s, хэш: %s, backet: %s", hashcode2, hash2, bucket2);
        System.out.println(System.lineSeparator());
        System.out.println(map.get(user1));
        System.out.println(map.get(user2));
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return children == user.children && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday);
    }*/

    @Override
    public int hashCode() {
        return Objects.hash(name, children, birthday);
    }
}
