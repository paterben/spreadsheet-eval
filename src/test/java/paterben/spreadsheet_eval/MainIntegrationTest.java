package paterben.spreadsheet_eval;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainIntegrationTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        // Redirect standard output to our stream
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        // Restore the original system stream
        System.setOut(originalOut);
    }

    @Test
    void testParseValidCsv() throws FileNotFoundException, IOException {
        // Arrange
        String csvData = "10, 1 3 +, 2 3 -\n" +
                "b1 b2 *, a1, b1 a2 / c1 +\n" +
                "+, 1 2 3, c3\n" +
                "1 7 /, 1 10 /, a4 b4 /";
        StringReader reader = new StringReader(csvData);

        // Act
        Main.processCsvSpreadsheet(reader);

        // Assert
        String expectedOutput = "10,4,-1\r\n" +
                "40,10,-0.9\r\n" +
                "#ERR,#ERR,#ERR\r\n" +
                "0.14286,0.1,1.42857\r\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }
}
