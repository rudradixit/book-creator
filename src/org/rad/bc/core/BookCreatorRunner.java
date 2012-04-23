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

package org.rad.bc.core;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.rad.bc.map.BookCreatorMapper;
import org.rad.bc.reduce.BookCreatorReducer;

public class BookCreatorRunner extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            System.err.println("No arguments were passed.");
        } else {
            for (String arg : args) {
                System.out.println("Arg: " + arg);
            }
        }

        if (args.length != 2) {
            System.err.printf("Usage: %s [generic options] <input> <output>\n", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }

        Job job = new Job();
        job.setJarByClass(BookCreatorRunner.class);
        job.setJobName("Book creator");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(BookCreatorMapper.class);
        job.setReducerClass(BookCreatorReducer.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(Text.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new BookCreatorRunner(), args);
        System.exit(exitCode);
    }
}
