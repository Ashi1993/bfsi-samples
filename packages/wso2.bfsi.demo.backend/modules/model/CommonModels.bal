
# Links relevant to the payload.
public type Links record {
    # Self Link relevant to the payload
    string Self;
    # First Link relevant to the payload
    string First?;
    # Prev Link relevant to the payload
    string Prev?;
    # Next Link relevant to the payload
    string Next?;
    # Last Link relevant to the payload
    string Last?;
};

# Meta Data relevant to the payload.
public type Meta record {
    # Count of Metadata
    int TotalPages?;
    # All dates in the JSON payloads are represented in ISO 8601 date-time format. 
    # All date-time fields in responses must include the timezone. An example is below:
    # 2017-04-05T10:43:07+00:00
    string FirstAvailableDateTime?;
    # All dates in the JSON payloads are represented in ISO 8601 date-time format. 
    # All date-time fields in responses must include the timezone. An example is below:
    # 2017-04-05T10:43:07+00:00
    string LastAvailableDateTime?;
};
