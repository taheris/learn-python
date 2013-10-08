package com.taheris.learnpython.python;

import java.io.StringWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.python.util.PythonInterpreter;
import com.taheris.learnpython.helpers.StringMethods;

public class Python {
    private final PythonInterpreter interpreter = new PythonInterpreter();
    private final StringWriter out = new StringWriter();

    public Python() {
        interpreter.setOut(out);
    }

    public PythonInterpreter getPython() {
        return interpreter;
    }

    public StringWriter getOut() {
        return out;
    }

    /** execute the given code, then return the results */
    public String exec(String code) throws TimeoutException, Exception {
        clearBuffer();
        runCode(code);
        return popBuffer();
    }

    /** execute the given code, adding results to the buffer */
    public void execBuffer(String code) throws TimeoutException, Exception {
        runCode(code);
    }

    /** retrieve the contents of the buffer */
    public String getBuffer() {
        return StringMethods.stripNewline(out.toString());
    }

    /** retrieve and clear the contents of the buffer */
    public String popBuffer() {
        String buffer = getBuffer();
        clearBuffer();
        return buffer;
    }

    /** clear the contents of the buffer */
    public void clearBuffer() {
        out.getBuffer().setLength(0);
    }

    /** run the code for 5 seconds, then throw a TimeoutException */
    private void runCode(final String code) throws TimeoutException, Exception {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return interpreterExec(code);
            }
        };
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Void> task = executor.submit(callable);

        try {
            task.get(5, TimeUnit.SECONDS);
        } finally {
            executor.shutdownNow();
        }
    }

    /** execute the given code using the Python interpreter */
    private Void interpreterExec(String code) {
        interpreter.exec(code);
        return null;
    }
}
