create keyspace bezkoder with replication ={'class':'SimpleStrategy', 'replication_factor':1};

use tutorial;

CREATE TABLE tutorial
(
    id          timeuuid PRIMARY KEY,
    title       text,
    description text,
    published   boolean
);


CREATE CUSTOM INDEX idx_title ON tutorial.tutorial (title)
    USING 'org.apache.cassandra.index.sasi.SASIIndex'
    WITH OPTIONS = {
        'mode': 'CONTAINS',
        'analyzer_class': 'org.apache.cassandra.index.sasi.analyzer.NonTokenizingAnalyzer',
        'case_sensitive': 'false'};