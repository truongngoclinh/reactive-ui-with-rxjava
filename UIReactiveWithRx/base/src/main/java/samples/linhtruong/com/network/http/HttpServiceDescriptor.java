package samples.linhtruong.com.network.http;

public abstract class HttpServiceDescriptor<T> {

    public abstract String getBaseUrl();

    public abstract Class<T> getServiceClass();

    @Override
    public final int hashCode() {
        int result = 17;
        String url = getBaseUrl();
        if (url != null) {
            result += 31 * result + url.hashCode();
        }

        Class<T> clazz = getServiceClass();
        if (clazz != null) {
            result += 31 * result + clazz.hashCode();
        }
        return result;
    }

    @Override
    public final boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HttpServiceDescriptor)) {
            return false;
        }
        HttpServiceDescriptor hs = (HttpServiceDescriptor) o;
        String url = hs.getBaseUrl();
        Class clazz = hs.getServiceClass();
        return url != null && url.equals(getBaseUrl()) && clazz != null && clazz == getServiceClass();
    }
}
