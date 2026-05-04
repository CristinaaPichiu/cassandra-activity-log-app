CREATE KEYSPACE IF NOT EXISTS activity_log_app
    WITH REPLICATION = {
        'class': 'NetworkTopologyStrategy',
        'datacenter1': '3'
        };

CREATE TABLE IF NOT EXISTS activity_log_app.user_activity_logs (
                                                                   user_id           text,
                                                                   activity_timestamp timestamp,
                                                                   activity_id       uuid,
                                                                   activity_type     text,
                                                                   PRIMARY KEY (user_id, activity_timestamp, activity_id)
) WITH CLUSTERING ORDER BY (activity_timestamp DESC, activity_id ASC);

-- Date de test
INSERT INTO activity_log_app.user_activity_logs (user_id, activity_timestamp, activity_id, activity_type)
VALUES ('user-001', toTimestamp(now()), uuid(), 'LOGIN');

INSERT INTO activity_log_app.user_activity_logs (user_id, activity_timestamp, activity_id, activity_type)
VALUES ('user-001', toTimestamp(now()), uuid(), 'VIEW_PRODUCT');

INSERT INTO activity_log_app.user_activity_logs (user_id, activity_timestamp, activity_id, activity_type)
VALUES ('user-001', toTimestamp(now()), uuid(), 'ADD_TO_CART');

INSERT INTO activity_log_app.user_activity_logs (user_id, activity_timestamp, activity_id, activity_type)
VALUES ('user-002', toTimestamp(now()), uuid(), 'LOGIN');

INSERT INTO activity_log_app.user_activity_logs (user_id, activity_timestamp, activity_id, activity_type)
VALUES ('user-002', toTimestamp(now()), uuid(), 'PURCHASE');

INSERT INTO activity_log_app.user_activity_logs (user_id, activity_timestamp, activity_id, activity_type)
VALUES ('user-002', toTimestamp(now()), uuid(), 'LOGOUT');

INSERT INTO activity_log_app.user_activity_logs (user_id, activity_timestamp, activity_id, activity_type)
VALUES ('user-003', toTimestamp(now()), uuid(), 'LOGIN');

INSERT INTO activity_log_app.user_activity_logs (user_id, activity_timestamp, activity_id, activity_type)
VALUES ('user-003', '2026-01-15 10:30:00+0000', uuid(), 'CHECKOUT');

INSERT INTO activity_log_app.user_activity_logs (user_id, activity_timestamp, activity_id, activity_type)
VALUES ('user-003', '2026-02-20 14:22:10+0000', uuid(), 'VIEW_PRODUCT');

SELECT * FROM activity_log_app.user_activity_logs;