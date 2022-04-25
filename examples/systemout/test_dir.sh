for f in ~/KLV_samples/*
do
    if test -f "$f"; then
        echo "Processing" "$f"
        java -jar target/systemout-2.0.0-SNAPSHOT-jar-with-dependencies.jar "$f" > "$(basename "$f").klv.txt"
    fi
done

