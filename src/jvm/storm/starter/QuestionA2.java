/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package storm.starter;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import storm.starter.bolt.PrinterBolt_Group16;
import storm.starter.bolt.PrinterBolt_Group16_Q2;
import storm.starter.spout.TwitterSampleSpout_Group16;
import storm.starter.spout.TwitterSampleSpout_Group16_Q2;

public class QuestionA2 {
    public static void main(String[] args) {
       /* String consumerKey = args[0];
        String consumerSecret = args[1];
        String accessToken = args[2];
        String accessTokenSecret = args[3];
        String[] arguments = args.clone();
        String[] keyWords = Arrays.copyOfRange(arguments, 4, arguments.length);*/

        String consumerKey = "PAENc9WI0klCBwLPhyOqfVNMt";
        String consumerSecret = "9BXbYQbXSjCRgMzU6UhgopMZe3aBVw7bjTS16fSMyTWef3QPyK";
        String accessToken = "3435593417-8EQvMbiAVpEQeZFR6uvJDk1c5Ow05ZudGbZrIpa";
        String accessTokenSecret = "4v2pFJykOjDfA14aGLKPMCHRSbyx0zXGGP9mygPJDM3I9";
 //       String[] keyWords = {"fans","halloween","star","club","apple","express","google","a","an","the"};
        String[] keyWords = {"apple","google","Microsoft","facebook","iphone","app","tech","ipad","mobile","android","ios",
                                "mac","imac","macbook","apps","music","itunes","games","AndroidGames","ipadgames","samsung",
                                "network","yahoo","amazon","uber","tvos","cloud","icloud","bestbuy","ebay","computer","phone",
                                "technology","ebook","java","chrome","whatsapp",
                                "iphone7","ios10"};


        TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("twitter", new TwitterSampleSpout_Group16_Q2(consumerKey, consumerSecret,
                                accessToken, accessTokenSecret, keyWords));
        builder.setBolt("print", new PrinterBolt_Group16_Q2())
                .shuffleGrouping("twitter");
                
                
        Config conf = new Config();
        
        
        LocalCluster cluster = new LocalCluster();
        
        cluster.submitTopology("test", conf, builder.createTopology());
        
        Utils.sleep(30000);
        cluster.shutdown();
    }
}
