package commons;

import lombok.SneakyThrows;
import lombok.val;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public interface TestUtils {

    @SneakyThrows
    static String fromSystemOut(Runnable runnable) {

        PrintStream realOut = System.out;

        try (val out = new ByteArrayOutputStream();
             val printStream = new PrintStream(out)) {

            System.setOut(printStream);
            runnable.run();

            return new String(out.toByteArray()); // .intern()

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.setOut(realOut);
        }
    }
}
