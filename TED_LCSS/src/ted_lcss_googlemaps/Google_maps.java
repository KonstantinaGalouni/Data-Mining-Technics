package ted_lcss_googlemaps;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.graphics.Image;


//http://git.eclipse.org/c/platform/eclipse.platform.swt.git/tree/examples/org.eclipse.swt.snippets/src/org/eclipse/swt/snippets/Snippet128.java

public class Google_maps {
    Properties property_file = new Properties();
    
    public void Browser()
    {
        /* Create main browser thread */
        Display display = new Display();
        
        /* Create main window */
	final Shell shell;
        shell = new Shell(display);
        shell.setText("Google Maps");
        
        /* add icon to main frame */
        Image global_icon;
        global_icon = new Image(display,"global-icon.png"); 
        shell.setImage(global_icon);
        
        /* Set chell maximized */
        shell.setMaximized(true);
        
        /* Set Layout to shell */
	GridLayout gridLayout = new GridLayout();
	gridLayout.numColumns = 3;
	shell.setLayout(gridLayout);
		
        /* Create toolbar */
        ToolBar toolbar = new ToolBar(shell, SWT.NONE);
        
        /* Create toolbar items, set icons */
	ToolItem itemStop = new ToolItem(toolbar, SWT.PUSH);
	itemStop.setText("Stop");
        Image stop_icon;
        stop_icon = new Image(display,"stop-icon.png");
        itemStop.setImage(stop_icon);
        
	ToolItem itemRefresh = new ToolItem(toolbar, SWT.PUSH);
	itemRefresh.setText("Refresh");
        Image refresh_icon;
        refresh_icon = new Image(display,"refresh-icon.png");
        itemRefresh.setImage(refresh_icon);
		
        /* Set toolbar Layout */
	GridData data = new GridData();
	data.horizontalSpan = 3;
	toolbar.setLayoutData(data);

        /* Create Browser */
	final Browser browser;
	try {
		browser = new Browser(shell, SWT.NONE);
	} catch (SWTError e) {
		System.out.println("Could not instantiate Browser: " + e.getMessage());
                display.dispose();
		return;
	}
	data = new GridData();
	data.horizontalAlignment = GridData.FILL;
	data.verticalAlignment = GridData.FILL;
	data.horizontalSpan = 3;
	data.grabExcessHorizontalSpace = true;
	data.grabExcessVerticalSpace = true;
	browser.setLayoutData(data);

        /* Create Status */
        final Label status = new Label(shell, SWT.NONE);
	data = new GridData(GridData.FILL_HORIZONTAL);
	data.horizontalSpan = 2;
	status.setLayoutData(data);

	/* Create Status Bar */	
        final ProgressBar progressBar = new ProgressBar(shell, SWT.NONE);
	data = new GridData();
	data.horizontalAlignment = GridData.END;
	progressBar.setLayoutData(data);

	/* event handling */
	Listener listener = new Listener() {
		@Override
		public void handleEvent(Event event) {
                    ToolItem item = (ToolItem)event.widget;
                    String string = item.getText();
                    switch (string) {
                        case "Stop":
                            browser.stop();
                            break;
                        case "Refresh":
                            browser.refresh();
                            break;
                    }
                }
	};
		
        browser.addProgressListener(new ProgressListener() {
		@Override
		public void changed(ProgressEvent event) {
                    if (event.total == 0) return;                            
                    int ratio = event.current * 100 / event.total;
                    progressBar.setSelection(ratio);
		}
                
                @Override
		public void completed(ProgressEvent event) {
			progressBar.setSelection(0);
		}
	});
	
        browser.addStatusTextListener(new StatusTextListener() {
		@Override
		public void changed(StatusTextEvent event) {
			status.setText(event.text);	
		}
	});
	
	itemStop.addListener(SWT.Selection, listener);
	itemRefresh.addListener(SWT.Selection, listener);
           
        /* Set javscript enabled */
        browser.setJavascriptEnabled( true );
		
	shell.open();

        /* Get URL from Property File */
        try{ 
             /* Load property file from project folder */ 
             property_file.load(new FileInputStream("settings.properties")); 
         } catch (IOException exception_IO) { 
             System.err.println("IO Exception occured while loading property file"); 
         } 
        
        browser.setUrl(property_file.getProperty("path_html_file"));
		
        while (!shell.isDisposed()) 
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
	}
	display.dispose();
    }
    
}
