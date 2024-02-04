// ECS610U -- Miles Hansard 2020

precision mediump float;

// light data
uniform struct {
    vec4 position, ambient, diffuse, specular;  
} light;

// material data
uniform struct {
    vec4 ambient, diffuse, specular;
    float shininess;
} material;

// clipping plane depths
uniform float near, far;

// normal, source and taget -- interpolated across all triangles
varying vec3 m, s, t;

 float scene_depth(float frag_z)
    {
        float ndc_z = 2.0*frag_z - 1.0;
        return (2.0*near*far) / (far + near - ndc_z*(far-near));
    }


void main()
{   
    if(gl_FragCoord.z < 0.5) {
        // don't render close fragments
        discard;
    }

    float z = scene_depth(gl_FragCoord.z);
    z = (12.0 - z) / 5.0;

    // renormalize interpolated normal
    vec3 n = normalize(m);

    // reflection vector
    vec3 r = -normalize(reflect(s,n));

    // phong shading components

    vec4 ambient = material.ambient * 
                   light.ambient * 0.0;

    vec4 diffuse = material.diffuse * 
                   max(dot(s,n),0.0) * 
                   light.diffuse;

    vec4 specular = material.specular *
        pow(max(dot(r,t),0.0), material.shininess) *
        light.specular;
    
    gl_FragColor = vec4((ambient + diffuse + specular).rgb, 1.0);
    //gl_FragColor = vec4(z, z, z, 1.0);
}

