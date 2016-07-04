/*
 * Copyright (c) 2016, Groupon, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * Neither the name of GROUPON nor the names of its contributors may be
 * used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.groupon.dse.kafka.policy

import com.groupon.dse.kafka.common.State

/**
  * Fetch policy defined by a combination of offsets and last consumed time
  *
  * @param offsetThreshold Offset threshold to enable fetch
  * @param timeThreshold Time threshold to enable fetch
  * @param fetchSize Kafka fetch size
  * @param startOffset Kafka start offset
  */
class TimeAndOffsetBasedFetchPolicy(
                                     offsetThreshold: Long,
                                     timeThreshold: Long,
                                     fetchSize: Int,
                                     startOffset: Long)
  extends FetchPolicy(fetchSize, startOffset) {

  override def toString: String = s"TimeAndOffsetBasedFetchPolicy = [" +
    s"offsetThreshold: $offsetThreshold, " +
    s"timeThreshold: $timeThreshold," +
    s"fetchSize: $fetchSize," +
    s"startOffset: $startOffset]"

  override def canFetch(local: State, global: State): Boolean =
    (local.timestamp - global.timestamp) <= timeThreshold || (local.offset - global.offset) <= offsetThreshold
}