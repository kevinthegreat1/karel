package stanford.karel;

import org.junit.jupiter.api.Test;

import java.awt.event.WindowEvent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class KarelTest extends SuperKarel {
	private KarelProgram program;

	@Test
	void testMain() {
		main(new String[0], this);
	}

	@Override
	public void start(String[] args) {
		CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> program.signalStarted());
		program = new KarelProgram();
		program.setStartupObject(this);
		program.start(args);
	}

	@Override
	public void run() {
		program.getJFrame().dispatchEvent(new WindowEvent(program.getJFrame(), WindowEvent.WINDOW_CLOSING));
	}
}
