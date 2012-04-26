/*
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * All of the source code for this project is ruled under the GNU General Public License, version 3.
 * Please refer to http://www.gnu.org/licenses/gpl-3.0.txt for additional information and legal text.
 *
 * This code is part of my Master dissertation for MOSS/ISCTE/Portugal.
 * Please contact me at rudradixit at gmail dot com.
 */

package org.rad.bc.reduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.*;
import java.net.URI;
import java.util.Iterator;

public class BookCreatorReducer extends Reducer<LongWritable, Text, Text, MapWritable> {
    @Override
    public void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Configuration config = new Configuration();
        Iterator<Text> textIterator = values.iterator();

        String outputPath = context.getConfiguration().get("mapred.output.dir") == null ?
                                                                    "" :
                                                                    context.getConfiguration().get("mapred.output.dir")
                                                                    + System.getProperty("file.separator");

        Text text;
        BufferedWriter bw;
        FileSystem fs;
        OutputStream out;

        while (textIterator.hasNext()) {
            text = textIterator.next();
            fs = FileSystem.get(URI.create("."), config);
            out = fs.create(new Path(outputPath + key.toString() + "_" + System.currentTimeMillis() + ".htm"));
            bw = new BufferedWriter(new OutputStreamWriter(out));
            bw.write(text.toString());
            bw.close();
        }
    }
}