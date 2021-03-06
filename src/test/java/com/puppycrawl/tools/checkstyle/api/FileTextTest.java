////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class FileTextTest {
    @Test
    public void testUnsupportedCharset() throws IOException {
        // just to make UT coverage 100%
        final String charsetName = "STRANGE_CHARSET";
        try {
            new FileText(new File("any name"), charsetName);
            fail("UnsupportedEncodingException is expected");
        }
        catch (IllegalStateException ex) {
            assertEquals("Unsupported charset: " + charsetName, ex.getMessage());
        }

    }

    @Test
    public void testSupportedCharset() throws IOException {
        final String charsetName = "ISO-8859-1";
        final FileText o = new FileText(new File("src/test/resources/com/puppycrawl/tools/"
                 + "checkstyle/api/import-control_complete.xml"), charsetName);
        assertEquals(charsetName, o.getCharset().name());
    }

    @Test
    public void testLineColumnBeforeCopyConstructor() throws IOException {
        final String charsetName = "ISO-8859-1";
        final FileText o = new FileText(new File("src/test/resources/com/puppycrawl/tools/"
                 + "checkstyle/api/import-control_complete.xml"), charsetName);
        final LineColumn lineColumn = o.lineColumn(100);
        final FileText copy = new FileText(o);
        assertEquals(lineColumn, copy.lineColumn(100));
    }

    @Test
    public void testLineColumnAfterCopyConstructor() throws IOException {
        final String charsetName = "ISO-8859-1";
        final FileText o = new FileText(new File("src/test/resources/com/puppycrawl/tools/"
                 + "checkstyle/api/import-control_complete.xml"), charsetName);
        final FileText copy = new FileText(o);
        assertEquals(3, copy.lineColumn(100).getLine());
    }
}
