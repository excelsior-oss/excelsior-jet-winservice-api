/*
 * @(#)WinService.java        0.01  06.08.02
 *
 * Copyright (c)2002 Exceslior, LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.excelsior.service;

/**
 *
 * The <code>WinService</code> class is an abstract class
 * representing Windows service.
 *
 * <p> To create your own service, you should subclass <code>WinService</code>
 * class and override the <code>run</code> method to implement a
 * service functionality. Optionally, you may override the <code>init,
 * shutdown, stop, pause, and resume</code> methods as well. These methods
 * are also referred to as Control Request Handlers (CRH).
 *
 * <p> The processing timeout can be set using the <code>setInitTimeout</code>,
 * <code>setPauseTimeout</code>, <code>setResumeTimeout</code>,
 * <code>setStopTimeout</code> methods.
 * The default timeout value is 10 seconds.
 * Note that timeout cannot be set for <code>shutdown</code> CRH method
 * because it is defined by the operating system (as less than 20 seconds).
 * 
 * <p> Upon service activation, its <code>init</code> CRH method is invoked.
 * If it is not completed in time, a warning message is logged
 * into the system event log and the <code>run</code> CRH method is invoked.
 * The <code>init</code> method returns a <code>boolean</code> result indicating
 * whether the service accepts the pause/resume control requests.
 *
 * <p> Each CRH method, including <code>init</code>, is run in
 * a separate thread.
 *
 * <p> Any exceptions thrown during a CRH method execution are ignored, so
 * you may wish to enclose the body of such a method into a try block
 * to handle exceptions:
 *
 * <p> <blockquote><pre>
 *     try {
 *         ...
 *     } catch (Throwable exception) {
 *        logErrorEvent (exception.toString());
 *     }
 * </pre></blockquote>
 *
 * <p> You may not instantiate subclasses of the WinService class explicitly.
 * The only instance of your service class is instantiated automatically
 * for each service process at the moment of its activation.
 * It is done using the method Class.newInstance(), so the default constructor
 * with public access modifier must be present in the subclass of the
 * WinService class.
 *
 *
 * @author  Alexander Markov
 * @version 0.01, 06.08.02
 * @since   JET 3.0
 */

abstract public class WinService {

     /**
     * Default constructor to create WinService instance.
     * At most one instance of a WinService subclass can be created
     * for each service process.
     * Note that the instance is created automatically during service start-up.
     * The subclass of the WinService class should declare the
     * default constructor with public access modifier.
     *
     */
    protected WinService () {
        instanceCount++;
        if (instanceCount > 1) {
            throw new Error ("Unable to create several instances of WinService");
        }
    }

    /**
    * Service initialization CRH.
    * Initalization time is limited to 10 seconds unless setInitTimeout
    * is called.
    *
    * @return    <code>true</code>  if the service supports pause/resume
    *                               control requests;
    *            <code>false</code> otherwise.
    *
    */
    protected boolean init () {
        return false;
    }

    /**
    * Main service routine.
    * It is called after initialization is completed.
    * However, if initialization is not completed in time, the <code>run</code>
    * method is called anyway after timeout specified.
    * The method is run in a separate thread. 
    * Upon exit of this method, the service stops executing.
    *
    */
    protected void run () {
    }

    /**
    * Service pause CRH.
    * The method is run in a separate thread. 
    * Timeout for completion of a pause request can be set using
    * <code>setPauseTimeout</code>.
    * Any exceptions thrown in this method are ignored.
    *
    */
    protected void pause () {
    }

    /**
    * Service resume CRH.
    * The method is run in a separate thread. 
    * Timeout for completion of resume request can be set using
    * <code>setResumeTimeout</code>.
    * Any exceptions thrown in this method are ignored.
    *
    */
    protected void resume () {
    }

    /**
    * Service stop CRH.
    * The method is run in a separate thread. 
    * Timeout for completion of stop request can be set using
    * <code>setStopTimeout</code>.
    * Any exceptions thrown in this method are ignored.
    *
    */
    protected void stop () {
    }

    /**
    * Service shutdown CRH.
    * The method is run in a separate thread. 
    * Competion timeout cannot be set for the shutdown request and
    * limited to less than 20 seconds.
    * Any exceptions thrown in this method are ignored.
    *
    */
    protected void shutdown () {
    }

    /**
    * Set initialization timeout.
    * The default initialization timeout is 10 seconds.
    *
    */
    protected final void setInitTimeout (int millis)
    {
        setTimeout (actionInit, millis);
    }

    /**
    * Set pause request completion timeout.
    * The default pause completion timeout is 10 seconds.
    *
    */
    protected final void setPauseTimeout (int millis)
    {
        setTimeout (actionPause, millis);
    }

    /**
    * Set resume request completion timeout.
    * The default resume completion timeout is 10 seconds
    *
    */
    protected final void setResumeTimeout (int millis)
    {
        setTimeout (actionResume, millis);
    }

    /**
    * Set stop request completion timeout.
    * The default stop completion timeout is 10 seconds
    *
    */
    protected final void setStopTimeout (int millis)
    {
        setTimeout (actionStop, millis);
    }

    /**
    * Retrieve the service command-line arguments.
    * @since JET 3.5
    */
    protected static native String[] getArgs();

    /**
    * Write an information message to the system event log.
    *
    */
    protected final void logInfoEvent (String message)
    {
        logEvent (eventInfo, message.getBytes());
    }

    /**
    * Write a warning message to the system event log.
    *
    */
    protected final void logWarningEvent (String message)
    {
        logEvent (eventWarning, message.getBytes());
    }

    /**
    * Write an error message to the system event log.
    *
    */
    protected final void logErrorEvent (String message)
    {
        logEvent (eventError, message.getBytes());
    }

    // *** Implementation ***

    private static int instanceCount = 0;

    private static final int actionInit     = 1;
    private static final int actionPause    = 2;
    private static final int actionResume   = 3;
    private static final int actionStop     = 4;
    private static final int actionShutdown = 5;

    private class ControlRequestHandlerThread extends Thread {
        private int action;

        ControlRequestHandlerThread (int action) {
            this.action  = action;
        }

        public void run () {
            try {
                switch (action) {
                    case actionInit:
                        WinService.this.acceptPauseResume = WinService.this.init ();
                        break;

                    case actionPause:
                        WinService.this.pause ();
                        break;

                    case actionResume:
                        WinService.this.resume ();
                        break;

                    case actionStop:
                        WinService.this.stop ();
                        break;

                    case actionShutdown:
                        WinService.this.shutdown ();
                        break;
                }
            } catch (Throwable ignore) {}

            WinService.this.actionCompleted (action);
        }
    }

    private void handleControlRequest (int action)
    {
        ControlRequestHandlerThread handlerThread = new ControlRequestHandlerThread (action);
        handlerThread.start ();
    }

    private void run0 ()
    {
        this.run();        
    }

    private native void setTimeout (int action, int newTimeout);


    private volatile boolean acceptPauseResume;

    private native void actionCompleted (int action);

    private static final int eventInfo    = 0;
    private static final int eventWarning = 1;
    private static final int eventError   = 2;

    private native void logEvent (int eventType, byte message[]);
}