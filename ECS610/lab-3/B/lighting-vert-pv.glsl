// ECS610U -- Miles Hansard 2020

// rotation/translation and camera matrix
uniform mat4 modelview, projection;

// light position and colour -- only need position here, but the 
// whole struct declaration must EXACTLY match that in the FS
uniform struct  {
    mediump vec4 position, ambient, diffuse, specular;
} light;

// material data
uniform struct {
    vec4 ambient, diffuse, specular;
    float shininess;
} material;

// incoming attributes
attribute vec3 vertex, normal;

// normal, source and target directions for the FS
varying vec3 m, s, t;
varying vec4 colour;

void main()
{
    // transform point to camera coordinates
    vec4 p = modelview * vec4(vertex,1.0);

    // transform normal to camera coordinates
    m = normalize(modelview * vec4(normal,0.0)).xyz;

    // light (already in camera coordinates) relative to vertex position
    s = normalize(light.position.xyz - p.xyz);

    // camera [0,0,0] relative to vertex position
    t = normalize(-p.xyz);

    // project point as usual
    gl_Position = projection * p;


        // renormalize interpolated normal
    vec3 n = normalize(m);

    // reflection vector
    vec3 r = -normalize(reflect(s,n));

    // phong shading components

    vec4 ambient = material.ambient * 
                   light.ambient;

    vec4 diffuse = material.diffuse * 
                   max(dot(s,n),0.0) * 
                   light.diffuse;

    // B1 -- IMPLEMENT SPECULAR TERM
    vec3 h = normalize(s+t);

    vec4 specular = material.specular *
        pow(max(dot(r,t),0.0), material.shininess) *
        light.specular;

    // B3 -- IMPLEMENT BLINN SPECULAR TERM
    vec4 blinn_specular = material.specular *
        pow(max(dot(h,n),0.0), material.shininess * 4.0) *
        light.specular;

    colour = vec4((ambient + diffuse + blinn_specular).rgb, 1.0);
}

