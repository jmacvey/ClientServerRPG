/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import Context.ClientContext;

/**
 * This class is meant only to associate specific intialization and exit I/Os
 * @author Jmacvey
 */
public class IOContext extends ClientContext
{

    public IOContext(IOMSG ioMsg)
    {
        super(ioMsg);
    }
    
}
