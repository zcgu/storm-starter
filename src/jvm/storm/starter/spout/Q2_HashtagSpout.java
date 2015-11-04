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
package storm.starter.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Q2_HashtagSpout extends BaseRichSpout {
  SpoutOutputCollector _collector;
  Random _rand;


  @Override
  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    _collector = collector;
    _rand = new Random();
  }

  @Override
  public void nextTuple() {

    String[] hashtags = new String[]{"apple","google","Microsoft","facebook","iphone","app","tech","ipad","mobile","android","ios",
            "mac","imac","macbook","apps","music","itunes","games","AndroidGames","ipadgames","samsung",
            "network","yahoo","amazon","uber","tvos","cloud","icloud","bestbuy","ebay","computer","phone",
            "technology","ebook","java","chrome", "iphone7","ios10","music","GameInsight","gameinsight","androidgames",
            "WebSummit2015","AMAs","IllShowYou","URGENT","AMAs","BIGBANG","BTS","PushAwardsKathNiels","PSYBwelta","PENNYSTOCKS",
            "TEAMBILLIONAIRE","WebSummit2015","tech","News","HowTo"};
/*
    String[] hashtags_subset = new String[hashtags.length/2];
    for(int i=0;i<hashtags.length/2;i++)
      hashtags_subset[i] = hashtags[_rand.nextInt(hashtags.length)];
*/
    List<String> hashtags_subset = new ArrayList<String>();
    for(int i=0;i<hashtags.length/2;i++)
      hashtags_subset.add(hashtags[_rand.nextInt(hashtags.length)]);

    _collector.emit(new Values(hashtags_subset));

      Utils.sleep(10000);
  }

  @Override
  public void ack(Object id) {
  }

  @Override
  public void fail(Object id) {
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("hashtags"));
  }


}