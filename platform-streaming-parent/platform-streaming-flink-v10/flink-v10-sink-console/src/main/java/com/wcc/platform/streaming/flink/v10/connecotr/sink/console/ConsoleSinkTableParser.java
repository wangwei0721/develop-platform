package com.wcc.platform.streaming.flink.v10.connecotr.sink.console;

import com.wcc.platform.streaming.flink.core.table.AbsTableParser;
import com.wcc.platform.streaming.flink.core.table.TableInfo;

import java.util.Map;

public class ConsoleSinkTableParser extends AbsTableParser {

    @Override
    public TableInfo getTableInfo(String tableName, String fieldsInfo, Map<String, String> props) {
        ConsoleSinkTable consoleSinkTable = new ConsoleSinkTable();
        consoleSinkTable.setName(tableName);
        return consoleSinkTable;
    }
}
