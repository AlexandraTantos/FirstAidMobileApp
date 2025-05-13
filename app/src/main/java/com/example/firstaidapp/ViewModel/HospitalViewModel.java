package com.example.firstaidapp.ViewModel;
public class HospitalViewModel {
    private String name;
    private String id;
    private String address;
    private boolean isOperational;
    private boolean isOpen;
    private Location location;
    private Viewport viewport;
    private Icon icon;

    public HospitalViewModel(String name, String id, String address, boolean isOperational,
                             boolean isOpen, Location location, Viewport viewport, Icon icon) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.isOperational = isOperational;
        this.isOpen = isOpen;
        this.location = location;
        this.viewport = viewport;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isOperational() {
        return isOperational;
    }

    public void setOperational(boolean operational) {
        isOperational = operational;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public static class Location {
        private double lat;
        private double lng;

        public Location(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public static class Viewport {
        private Location northeast;
        private Location southwest;

        public Viewport(Location northeast, Location southwest) {
            this.northeast = northeast;
            this.southwest = southwest;
        }

        public Location getNortheast() {
            return northeast;
        }

        public void setNortheast(Location northeast) {
            this.northeast = northeast;
        }

        public Location getSouthwest() {
            return southwest;
        }

        public void setSouthwest(Location southwest) {
            this.southwest = southwest;
        }
    }

    public static class Icon {
        private String name;
        private String backgroundColor;
        private String maskBaseUri;

        public Icon(String name, String backgroundColor, String maskBaseUri) {
            this.name = name;
            this.backgroundColor = backgroundColor;
            this.maskBaseUri = maskBaseUri;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public String getMaskBaseUri() {
            return maskBaseUri;
        }

        public void setMaskBaseUri(String maskBaseUri) {
            this.maskBaseUri = maskBaseUri;
        }
    }
}
