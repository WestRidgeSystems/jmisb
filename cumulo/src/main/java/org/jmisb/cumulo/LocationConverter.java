package org.jmisb.cumulo;

/**
 * Methods for converting between WGS84 ellipsoid and ECEF coordinates
 *
 * Credit: http://danceswithcode.net/engineeringnotes/geodetic_to_ecef/geodetic_to_ecef.html
 */
public class LocationConverter {
    private static final double a = 6378137.0;               // WGS84 semi-major axis
    private static final double e2 = 6.6943799901377997e-3;  // WGS84 first eccentricity squared
    private static final double a1 = 4.2697672707157535e+4;  // a1 = a*e2
    private static final double a2 = 1.8230912546075455e+9;  // a2 = a1*a1
    private static final double a3 = 1.4291722289812413e+2;  // a3 = a1*e2/2
    private static final double a4 = 4.5577281365188637e+9;  // a4 = 2.5*a2
    private static final double a5 = 4.2840589930055659e+4;  // a5 = a1+a3
    private static final double a6 = 9.9330562000986220e-1;  // a6 = 1-e2

    /**
     * Convert Earth-Centered-Earth-Fixed (ECEF) to lat, Lon, Altitude
     *
     * @param ecef ECEF (x, y, z) in meters
     * @return 3D array containing lat, lon (rads), and alt (m HAE)
     */
    public static double[] ecefToGeo(double[] ecef) {
        double[] geo = new double[3];
        double x = ecef[0];
        double y = ecef[1];
        double z = ecef[2];
        double zp = Math.abs(z);
        double w2 = x * x + y * y;
        double w = Math.sqrt(w2);
        double r2 = w2 + z * z;
        double r = Math.sqrt(r2);
        geo[1] = Math.atan2(y, x);
        double s2 = z * z / r2;
        double c2 = w2 / r2;
        double u = a2 / r;
        double v = a3 - a4 / r;
        double s, c, ss;
        if (c2 > 0.3) {
            s = (zp / r) * (1.0 + c2 * (a1 + u + s2 * v) / r);
            geo[0] = Math.asin(s);
            ss = s * s;
            c = Math.sqrt(1.0 - ss);
        } else {
            c = (w / r) * (1.0 - s2 * (a5 - u - c2 * v) / r);
            geo[0] = Math.acos(c);
            ss = 1.0 - c * c;
            s = Math.sqrt(ss);
        }
        double g = 1.0 - e2 * ss;
        double rg = a / Math.sqrt(g);
        double rf = a6 * rg;
        u = w - rg * c;
        v = zp - rf * s;
        double f = c * u + s * v;
        double m = c * v - s * u;
        double p = m / (rf / g + f);
        geo[0] = geo[0] + p;
        geo[2] = f + m * p / 2.0;
        if (z < 0.0) {
            geo[0] *= -1.0;
        }
        return (geo);
    }

    /**
     * Convert Lat, Lon, Altitude to Earth-Centered-Earth-Fixed (ECEF)
     *
     * @param geo 3D array containing lat, lon (rads), and alt (m HAE)
     * @return ECEF (x, y, z) in meters
     */
    public static double[] geoToEcef(double[] geo) {
        double[] ecef = new double[3];
        double lat = geo[0];
        double lon = geo[1];
        double alt = geo[2];
        double n = a / Math.sqrt(1 - e2 * Math.sin(lat) * Math.sin(lat));
        ecef[0] = (n + alt) * Math.cos(lat) * Math.cos(lon);
        ecef[1] = (n + alt) * Math.cos(lat) * Math.sin(lon);
        ecef[2] = (n * (1 - e2) + alt) * Math.sin(lat);
        return (ecef);
    }
}
