package net.goorder.app.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.inject.Inject;
import org.jooq.DSLContext;
import static net.goorder.db.jooq.Tables.*;

/**
 *
 * @author witoldsz
 */
public class GroupIdGenerator {

    private final Random random = new Random();

    @Inject
    private DSLContext jooq;

    //TODO: integration test (refactoring required - extract random)
    public String next() {
        String result = null;
        while (result == null) {
            String candidate = generateRandomString();
            boolean unique = jooq.fetchOne(ORDERING_TABLE, ORDERING_TABLE.GROUP_ID.eq(candidate)) == null;
            result = unique ? candidate : null;
        }
        return result;
    }

    private String generateRandomString() {
        byte[] bytes = new byte[8];
        random.nextBytes(bytes);
        byte[] md = md5(bytes);
        String result = String.format("%02x%02x-%02x%02x", md[0], md[1], md[2], md[3]);
        return result;
    }

    private byte[] md5(byte[] bytes) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return md5.digest(bytes);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String... args) {
        GroupIdGenerator thiz = new GroupIdGenerator();
        Set<String> x = new HashSet<>();
        for (int i = 0; i < 100_000; ++i) {
            String s = thiz.next();
            if (!x.add(s)) {
                System.out.println("i=" + i + ": " + s);
            }
        }
    }
}
