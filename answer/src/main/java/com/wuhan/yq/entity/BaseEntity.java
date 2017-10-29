/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.wuhan.yq.entity;

import java.io.*;

/**
 * 
 * 实体对象基类
 * 
 * @author lzy
 * @version [版本号, 2017年5月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BaseEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    @SuppressWarnings("unchecked")
    public <T> T copyEntity()
    {
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try
        {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            
            ObjectInputStream in = new ObjectInputStream(byteIn);
            T result = (T)in.readObject();
            return result;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (byteArrayOutputStream != null)
            {
                try
                {
                    byteArrayOutputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (objectOutputStream != null)
            {
                try
                {
                    objectOutputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
