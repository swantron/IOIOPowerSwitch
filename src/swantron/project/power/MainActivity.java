package swantron.project.power;

import swantron.project.power.*;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.AbstractIOIOActivity;
import android.os.Bundle;
import android.widget.ToggleButton;

/**
 * Main activity of IOIOPowerSwitch project.
 * 
 * App displays a toggle button on the screen, provides digital out to two pins using
 * the {@link AbstractIOIOActivity} class.
 */
public class MainActivity extends AbstractIOIOActivity {
	private ToggleButton button_;

	/**
	 * Called upon creation for initialization
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		button_ = (ToggleButton) findViewById(R.id.button);
	}

	/**
	 * Primary thread...runs until interrupted.
	 */
	class IOIOThread extends AbstractIOIOActivity.IOIOThread {
		/** Define pins for out. */
		private DigitalOutput pin_1;
		private DigitalOutput pin_2;

		/**
		 * Called every time a connection with IOIO has been established.
		 * (opens pins)
		 * 
		 * @throws ConnectionLostException
		 * (if IOIO connection is lost)
		 */
		@Override
		protected void setup() throws ConnectionLostException {
			pin_1 = ioio_.openDigitalOutput(23, DigitalOutput.Spec.Mode.OPEN_DRAIN, true);
			pin_2 = ioio_.openDigitalOutput(25, DigitalOutput.Spec.Mode.OPEN_DRAIN, true);
		}

		/**
		 * Loop section
		 */
		@Override
		protected void loop() throws ConnectionLostException {
			pin_1.write(!button_.isChecked());
			pin_2.write(!button_.isChecked());
			try {
				sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * A method to create our IOIO thread.
	 * Taken from IOIO example app
	 */
	@Override
	protected AbstractIOIOActivity.IOIOThread createIOIOThread() {
		return new IOIOThread();
	}
}
